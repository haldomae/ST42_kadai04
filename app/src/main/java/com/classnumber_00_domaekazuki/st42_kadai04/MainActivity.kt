package com.classnumber_00_domaekazuki.st42_kadai04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.classnumber_00_domaekazuki.st42_kadai04.ui.theme.ST42_kadai04Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // データベースを作成(アプリ起動時に1度だけ実行)
        // AppDatabaseで作った関数を呼び出す
        val database = getDatabase(this)

        setContent {
            ST42_kadai04Theme {
                MemoApp(database)
            }
        }
    }
}
@Composable
fun MemoApp(database : AppDatabase){
    // 画面の状態を管理する変数たち(ViewModelに入れた方がよい)
    // 現在表示されているメモのリスト
    var memos by remember { mutableStateOf(listOf<Memo>()) }
    // ユーザが入力中のテキスト
    var newMemoText by remember { mutableStateOf("") }

    // 非同期の処理(DB処理)を実行する為の道具
    val scope = rememberCoroutineScope()

    // アプリ起動時にDBからメモを読み取る
    // アプリ起動時に1度だけ実行
    LaunchedEffect(Unit) {
        // メモの全データを取得する
        memos = database.memoDao().getAll()
    }

    // 画面のレイアウト
    Column(
        modifier = Modifier
            .fillMaxSize() // 画面全体を使用
            .padding(16.dp) // 周りに余白
    ) {
        // アプリタイトル
        Text(
            text = "メモ",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(16.dp)
        )
        // メモを入力する箇所
        Row {
            TextField(
                // 現在の入力内容
                value = newMemoText,
                // 入力欄の内容が変わった時
                onValueChange = {
                    newMemoText = it
                },
                // 入力欄のラベル
                label = {
                    Text(
                        text = "新しいメモ"
                    )
                }
            )
            Button(
                // ボタンが押された時
                onClick = {
                    // 入力欄が空白でないとき
                    if(newMemoText.isNotBlank()){
                        // 非同期の処理を開始
                        scope.launch {
                            // データベースにメモを登録
                            database.memoDao().insert(Memo(text = newMemoText))
                            // 画面を最新の状態にする
                            memos = database.memoDao().getAll()
                            // 入力欄を空にする
                            newMemoText = ""
                        }

                    }
                }
            ) { }
        }
    }
}