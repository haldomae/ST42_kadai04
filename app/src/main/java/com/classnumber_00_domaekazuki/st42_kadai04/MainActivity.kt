package com.classnumber_00_domaekazuki.st42_kadai04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
        // データベースを作成
        // AppDatabaseで作った関数を呼び出す
        val database = getDatabase(context = this)
        setContent {
            ST42_kadai04Theme {
                MemoApp(
                    database = database
                )
            }
        }
    }
}
@Composable
fun MemoApp(database: AppDatabase){
    // 画面の状態を管理する変数達(ViewModelに入れた方がいい)
    // 現在表示されているメモのリスト
    var memos by remember { mutableStateOf(listOf<Memo>()) }
    // ユーザが入力中のテキスト
    var newMemoText by remember { mutableStateOf("") }
    // 非同期で処理(DB処理)を実行する為の道具
    val scope = rememberCoroutineScope()

    // アプリ起動時にDBからメモを読み取る
    // アプリ起動時に一度だけ実行
    LaunchedEffect(Unit) {
        // メモの全データを取得する
        memos = database.memoDao().getAll()
    }

    // 画面のレイアウト
    Column(
        modifier = Modifier
            .fillMaxSize() // 画面全体を使用
            .padding(16.dp)
    ) {
        // アプリのタイトル
        Text(
            text = "メモ",
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
                label = {
                    Text(
                        text = "新しいメモ"
                    )
                }
            )
            Button(
                // ボタンが押されたとき
                onClick = {
                    // 入力欄が空ではないとき
                    if(newMemoText.isNotBlank()){
                        scope.launch {
                            // データベースにメモを登録
                            database.memoDao().insert(
                                memo = Memo(text = newMemoText)
                            )
                            // 画面の状態を最新にしておく
                            memos = database.memoDao().getAll()
                            // 入力欄を空
                            newMemoText = ""
                        }

                    }
                }
            ) {
                Text(
                    text = "登録"
                )
            }
        }

        // 保存されているメモの一覧
        // スクロール可能
        LazyColumn {
            items(memos) { memo ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // カード形式で表示
                    Row {
                        Text(
                            text = memo.text
                        )
                    }
                }
            }
        }
    }
}