package com.example.basiccodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccodelab.ui.theme.BasicCodelabTheme
import java.util.Collections.list
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.coerceAtLeast

//これはMainActivityというクラスがComponentActivityというクラスを継承しているということです。
//ComponentActivity()は何か？　公式：https://developer.android.com/reference/androidx/activity/ComponentActivity
//ComponentActivity は「家」だと思ってください。この家の中に「家具（＝アプリの画面やUI）」を置いて、みんなが快適に過ごせるようにします。
//
//昔の方法（従来のActivity）：
//昔は、家の中に家具を置くとき、たくさんの手順が必要でした。
// 例えば、「この場所にテーブルを置く」とか「ここにテレビを置く」といった感じで、全ての家具を一つ一つ指定して配置していました。
// これが setContentView を使って画面を作る方法です。
//
//新しい方法（ComponentActivity + Jetpack Compose）：
//ComponentActivity は、新しいスタイルの家だと考えてください。この家では、簡単な一言で「リビングを整える」とか「ダイニングをセットする」
// と言うだけで、家の中が自動的に快適なレイアウトに整います。これが setContent を使って画面を作る方法です。
//
//ComponentActivity の特徴
//Jetpack Compose での画面作り
//
//ComponentActivity は、家（アクティビティ）の中に家具（画面）を「簡単に」配置する方法を提供します。昔のように細かく指示を出さなくても、「リビングのレイアウトをお願い」って言えば、自動的にリビングが整うように、setContent という簡単な命令で画面を作れるんです。
//家のメンテナンスも自動でやってくれる
//
//さらに、この家（ComponentActivity）は、例えば「停電が起きた時」や「引っ越しをした時」にも、
// 自動で家具を元に戻してくれたり、家をちゃんと整えてくれます。これは、スマホが回転したり、他のアプリに切り替えられた時に、
// 画面を再描画したりすることに相当します。

//まとめ
//ComponentActivity は、新しいタイプの家で、Jetpack Compose という簡単で柔軟な方法で家具（画面）を整えられるようにしてくれます。
//ちなみにレンサの開発ではAppCompatActivity()を継承しているCustomActivity()というオリジナルのクラスを各アクティビティが継承しています。
class MainActivity : ComponentActivity() {

    //override fun onCreate(savedInstanceState: Bundle?) { は、Android の Activity クラスで非常に重要なメソッドです。
    // ここで、各部分を簡単に説明します。
    //
    //1. override
    //override は、親クラス で定義されたメソッドを 子クラス で再定義（オーバーライド）するという意味です。
    // ここでは、親クラスの onCreate メソッドをカスタマイズしています。
    //例えば、AppCompatActivity や ComponentActivity の親クラスには、もともと onCreate というメソッドがあり、
    // それをアプリ独自の処理に合わせて上書きしています。
    //
    //2. fun
    //fun は、Kotlin で関数を定義するためのキーワードです。ここでは onCreate という関数を定義しています。
    //
    //3. onCreate(savedInstanceState: Bundle?)
    //onCreate は、Android アクティビティのライフサイクルメソッドの1つです。アクティビティが 最初に作成される 時に呼ばれます。
    // つまり、アプリが起動されたり、画面が初めて表示されるときに実行されるメソッドです。
    //savedInstanceState: Bundle? は、画面の状態を一時的に保存したデータが渡されることがあります。
    // これにより、アプリが一時的に停止した後でも（例えば、画面の回転など）、前の状態を復元できます。
    // このパラメータが null ではない場合、以前の状態がここに入っています。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1. setContent { ... }
        //setContent は、Jetpack Compose において 画面の中身（UI） を指定するためのメソッドです。
        // この中に、画面の構成要素を定義します。
        //従来の方法では、setContentView(R.layout.activity_main) のように、XML ファイルで画面のレイアウトを指定していましたが、
        // Jetpack Compose では Kotlin のコード内に UI を直接書きます。
        //2. BasicCodelabTheme { ... }
        //BasicCodelabTheme は、アプリにテーマ（色やスタイルなど）を適用するためのラッパー関数です。
        // 通常、この関数はアプリ全体のデザインの統一感を保つために使います。
        //例えば、アプリの背景色や文字の色など、テーマに応じて定義されたスタイルが適用されます。
        //3. modifier = Modifier.fillMaxSize()
        //modifier は、UI の大きさや位置、スタイルなどを設定するためのオプションです。
        // この場合、Modifier.fillMaxSize() は、表示する内容が画面全体を覆うように設定しています。
        //つまり、MyApp の UI 要素がデバイスの画面全体を使うように指定されています。
        setContent {
            BasicCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
//Modifier は、Jetpack Compose で UI コンポーネントの 外観や動作 をカスタマイズするためのオブジェクトです。
// 簡単に言うと、Modifier は、コンポーネント（ボタンやテキストなど）の 大きさ、配置、装飾 などを調整するための
// 「ツールボックス」のようなものです。
//
//具体的に Modifier は何をする？
//Modifier を使うことで、次のような操作ができます：
//
//サイズの調整: UI要素の幅や高さを指定する。
//
//例: Modifier.fillMaxWidth() は、画面の横幅いっぱいに広げる。
//位置の指定: UI要素をどこに配置するかを指定する。
//
//例: Modifier.padding(16.dp) は、UI要素に余白をつける。
//見た目のカスタマイズ: 背景色や枠線をつける。
//
//例: Modifier.background(Color.Red) は、背景色を赤にする。
//クリックやタッチの処理: ユーザーがタップしたときの動作を設定する。
//
//例: Modifier.clickable { /* クリックされた時の処理 */ }
//その他のアクションの追加: スクロールや回転、透過度なども設定可能。
fun MyApp(modifier: Modifier = Modifier){
    //このコード var shouldShowOnboarding by remember { mutableStateOf(true) } は、Jetpack Compose で
    // 状態を管理するための仕組みを使っています。具体的に、shouldShowOnboarding という変数が初期状態で true に設定されており、
    // これによって「オンボーディング画面を表示するかどうか」を制御しています。
    //
    //1. var shouldShowOnboarding
    //shouldShowOnboarding は、オンボーディング画面を表示するかどうかを表す 変数 です。
    //true のときはオンボーディング画面が表示され、false のときは他の画面が表示されるように、画面の状態をコントロールします。
    //2. by キーワード
    //by は Kotlin の デリゲーション という機能で、shouldShowOnboarding の値が変わったときに自動的に反映されるようにします。
    // これによって、変数の値を直接書き換えたり、読み出すことが簡単になります。
    //3. remember { mutableStateOf(true) }
    //remember は、Jetpack Compose で 画面が再描画されても値を保持するための仕組み です。通常、UI が再描画されると、
    // 変数はリセットされてしまうことが多いのですが、remember を使うことで状態が保持されます。
    //
    //mutableStateOf(true) は、true という初期値を持った 状態（State）を作ります。
    // この状態は、値が変わると自動的に再描画を引き起こす仕組みになっています。
    //
    //mutableStateOf の役割： 例えば、ユーザーが「次へ」を押したときに shouldShowOnboarding の値が
    // false に変わったとすると、それに応じて画面が変わります。この値が変更されるたびに、UIが自動的に更新されます。
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    //このコードは、Jetpack Compose で UI の内容を 条件に応じて動的に切り替える という仕組みを実現しています。
    // 具体的には、画面に オンボーディング画面 を表示するか、メイン画面(Greetings) を表示するかを制御しています。
    //
    //1. Surface(modifier)
    //Surface は、Jetpack Compose で UI コンポーネントを囲むための「コンテナ（入れ物）」の役割をします。
    // ここに表示される UI 要素の背景やレイアウトを定義することができます。たとえば、背景色や影を設定することも可能です。
    //modifier は、どのようにレイアウトをするかの設定（例えば、サイズや配置）を外部から変更できるようにしたオプションです。
    // この場合、Surface に渡されている modifier が、レイアウトの見た目やサイズを調整します。

    //2. if (shouldShowOnboarding)
    //if 文は、条件によって処理を分岐させるためのものです。ここでは、shouldShowOnboarding という変数が true か false かによって、
    // 表示する画面を切り替えています。
    //true の場合: オンボーディング画面を表示する（初回起動時の説明画面のようなもの）。
    //false の場合: メインの画面 (Greetings() 関数) を表示する。
    //3. OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    //OnboardingScreen は、オンボーディング画面を表示するための コンポーザブル関数 です。この中で「続ける」ボタンがあり、
    // そのボタンをクリックすると、画面が次に進むようになっています。
    //onContinueClicked = { shouldShowOnboarding = false } は、「次へ」をクリックしたときの動作を定義しています。
    // この処理では、shouldShowOnboarding の値を false に変更します。これにより、次回画面が描画されるときに、
    // オンボーディング画面が表示されず、メイン画面に切り替わります。
    //4. Greetings()
    //Greetings() は、オンボーディングが終わった後に表示されるメイン画面です。OnboardingScreen が表示されなくなると、
    // この Greetings() 関数が呼ばれて、アプリのメイン部分が表示されるようになります。
    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardindScreen( onContinueClicked = { shouldShowOnboarding = false})
        }else {
            Greetings()
        }
    }
}


//このコードは、Jetpack Compose を使って オンボーディング画面（アプリの最初に表示される画面）を作成しています。
// 画面上に「ようこそメッセージ」と「次へ進むボタン」を配置しています。

//@Composable は、この関数が Composable 関数 であることを示します。
// これはUI を構築するための特別な関数で、Jetpack Compose で使われるものです。
@Composable
fun OnboardindScreen(
    //onContinueClicked: () -> Unit
    //onContinueClicked は、この画面で「次へ進む」ボタンがクリックされたときに実行される処理を指定するための引数です。
    // 関数を受け取る関数のようなもので、引数として「何かがクリックされたときにやるべきこと」を外部から渡すことができます。
    //() -> Unit という形は、引数を持たず、何かの結果を返すこともない（Unit = 何も返さない）関数型です
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    //Column は、縦に UI コンポーネントを並べるためのレイアウトです。この中に Text や Button を配置しています
    Column (
        //Column が画面全体に広がるように設定されています。
        modifier = modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center と horizontalAlignment = Alignment.CenterHorizontally によって、
        // 子コンポーネント（Text と Button）が画面の中央に表示されるようにしています。
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome to the Basics Codelab")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            // onClick = onContinueClicked
            //onClick は、ボタンがクリックされたときに実行される動作 を定義するプロパティです。ボタンがタップされると、
            // onClick に指定された処理が実行されます。
            //onContinueClicked は、外部から渡される関数で、ユーザーがボタンをクリックしたときに実行する動作をこの関数に任せています。
            // この関数の中身は、ボタンが押されたときに何をするかを外部で決めることができます。
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}


@Composable
private fun Greetingss(
    modifier : Modifier = Modifier,
    names: List<String> = List(10) {"it"}
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicCodelabTheme {
        OnboardindScreen(onContinueClicked = {} )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding (vertical = 4.dp, horizontal = 8.dp)
    ){
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row (
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sad do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expanded) {
                    stringResource(R.string.show_less)
                }else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//    val extraPadding by animateDpAsState(
//        if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioHighBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
//            ) {
//                Text(text = "Hello")
//                Text(text = name,
//                    style = MaterialTheme.typography.headlineMedium,
//                        fontWeight = FontWeight.ExtraBold
//                )
//            }
//            ElevatedButton(
//                onClick = { expanded = !expanded }
//            ) {
//                Text(if (expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more))
//            }
//        }
//    }


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview(){
    BasicCodelabTheme{
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(10) {"$it"}
){
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)){
        items(items = names){name ->
            Greeting(name = name)
        }
    }
}

@Preview
@Composable
fun GreetingssPreview(){
    BasicCodelabTheme {
        Greetingss(Modifier.fillMaxSize())
    }
}
