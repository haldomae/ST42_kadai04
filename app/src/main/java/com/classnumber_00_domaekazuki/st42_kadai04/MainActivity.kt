package com.classnumber_00_domaekazuki.st42_kadai04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                // 画面遷移のコントローラーを作成
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "memo"
                    ){
                    composable("memo") {
                        MemoApp(database, navController)
                    }
                    composable("detail/{memoText}") { backStackEntry ->
                        val memoText = backStackEntry.arguments?.getString("memoText")?.toString()
                        MemoDetail(memoText.toString())
                    }
                }
            }
        }
    }
}
// メモ追加、メモ一覧
@Composable
fun MemoApp(database : AppDatabase, navController: NavController){
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
            ) {
                Text(
                    text = "メモ追加"
                )
            }
        }

        // 保存されているメモの一覧
        // スクロール可能
        LazyColumn {
            items(memos) { memo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            navController.navigate("detail/${memo.text}")
                        }
                ) {
                    // カード形式で表示
                    Row {
                        Text(
                            text = memo.text
                        )
                        // 削除ボタン
                        Button(
                            // 削除ボタンが押された時
                            onClick = {
                                // メモDaoに入っている削除処理を実行する
                                // 削除処理はMemoオブジェクトを渡してあげる

                                // 登録処理と同様に表示されているデータを更新
                            }
                        ){
                            Text(
                                text = "削除"
                            )
                        }
                    }
                }
            }
        }
    }
}

// メモ詳細画面
@Composable
fun MemoDetail(memoText: String){
    Column() {
        Text(
            text = memoText
        )
        Button(
            onClick = {
                // メモ一覧に戻る
            }
        ) {
            Text(
                text = "戻る"
            )
        }
    }
}