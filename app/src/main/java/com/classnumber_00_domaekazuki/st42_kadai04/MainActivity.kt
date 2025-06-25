package com.classnumber_00_domaekazuki.st42_kadai04

// MainActivity.kt - ユーザーが見る画面とデータベースをつなぐファイル
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔍 データベースを作成（アプリ起動時に1回だけ）
        val database = getDatabase(this)  // 先ほど作った関数を呼び出し

        // 画面を表示
        setContent {
            MemoApp(database)  // データベースを画面に渡す
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoApp(database: AppDatabase) {
    // 🔍 画面の状態を管理する変数たち
    // memos = 現在表示されているメモのリスト
    var memos by remember { mutableStateOf(listOf<Memo>()) }
    // newMemoText = ユーザーが入力中のテキスト
    var newMemoText by remember { mutableStateOf("") }
    // scope = 非同期処理（データベース操作）を実行するための道具
    val scope = rememberCoroutineScope()

    // 🔍 アプリ起動時にデータベースからメモを読み込み
    LaunchedEffect(Unit) {  // アプリ起動時に1回だけ実行
        memos = database.memoDao().getAll()  // データベースから全メモを取得
    }

    // 🔍 画面のレイアウト開始
    Column(
        modifier = Modifier
            .fillMaxSize()      // 画面全体を使用
            .padding(16.dp)     // 周りに16dpの余白
    ) {
        // 📝 アプリのタイトル
        Text(
            text = "メモ",
            style = MaterialTheme.typography.headlineMedium,  // 大きな文字
            modifier = Modifier.padding(bottom = 16.dp)       // 下に余白
        )

        // 🔍 新しいメモを入力・追加する部分
        Row(
            modifier = Modifier.fillMaxWidth(),  // 横幅いっぱい
            horizontalArrangement = Arrangement.spacedBy(8.dp)  // 要素間に8dpの隙間
        ) {
            // テキスト入力欄
            TextField(
                value = newMemoText,                    // 現在の入力内容
                onValueChange = { newMemoText = it },   // 入力が変わった時の処理
                label = { Text("新しいメモ") },          // 入力欄のラベル
                modifier = Modifier.weight(1f)          // 残りの幅を全部使う
            )

            // 追加ボタン
            Button(
                onClick = {  // ボタンが押された時の処理
                    if (newMemoText.isNotBlank()) {  // 空文字でなければ
                        scope.launch {  // 非同期処理を開始
                            // 🔍 データベースに新しいメモを保存
                            database.memoDao().insert(Memo(text = newMemoText))
                            // 🔍 画面を最新の状態に更新
                            memos = database.memoDao().getAll()
                            // 🔍 入力欄をクリア
                            newMemoText = ""
                        }
                    }
                }
            ) {
                Text("追加")
            }
        }

        // 余白
        Spacer(modifier = Modifier.height(16.dp))

        // 🔍 保存されているメモの一覧表示
        LazyColumn(  // スクロール可能なリスト
            verticalArrangement = Arrangement.spacedBy(8.dp)  // 項目間に8dpの隙間
        ) {
            items(memos) { memo ->  // memosリストの各項目に対して
                Card(  // カード形式で表示
                    modifier = Modifier.fillMaxWidth()  // 横幅いっぱい
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),  // カード内に余白
                        horizontalArrangement = Arrangement.SpaceBetween  // 左右に分散配置
                    ) {
                        // メモのテキスト表示
                        Text(
                            text = memo.text,           // データベースから取得したテキスト
                            modifier = Modifier.weight(1f)  // 残りの幅を使用
                        )

                        // 削除ボタン
                        Button(
                            onClick = {  // 削除ボタンが押された時
                                scope.launch {  // 非同期処理を開始
                                    // 🔍 データベースから該当メモを削除
                                    database.memoDao().delete(memo)
                                    // 🔍 画面を最新の状態に更新
                                    memos = database.memoDao().getAll()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error  // 赤色
                            )
                        ) {
                            Text("削除")
                        }
                    }
                }
            }
        }
    }
}