package com.classnumber_00_domaekazuki.st42_kadai04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.classnumber_00_domaekazuki.st42_kadai04.ui.theme.ST42_kadai04Theme

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
    val newMemoText by remember { mutableStateOf("") }
    // 非同期で処理(DB処理)を実行する為の道具
    val scope = rememberCoroutineScope()

    // アプリ起動時にDBからメモを読み取る
    // アプリ起動時に一度だけ実行
    LaunchedEffect(Unit) {
        // メモの全データを取得する
        memos = database.memoDao().getAll()
    }
}