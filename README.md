# AndroidPractice - первое приложение на андроид
Приложение для практики на Java
Разработанное Android-приложение состоит из 2х активити: MainActivity и web.
## 1.1 MainActivity
MainActivity – является первым activity, на которое попадает пользователь при запуске приложения. Интерфейс activity описан в п.3.1.
### 1.1.1 Алгоритм работы activity
При вводе данных и нажатии кнопки происходит сверка текста в полях и в локальной “базе данных”. Если введённая пара “логин-пароль” совпадают с одной из хранящихся пар, пользователь попадает на следующую activity WebActivity. Иначе пользователь увидит появляющуюся надпись с текстом «Неверный логин или пароль, повторите попытку». В случае, если при нажатии кнопки поля пусты, сообщение о неправильности введённых данных будет так же показанно.
Локальная “база данных” представляет собой 3 расположенных (в ресурсах) в файле arrays.xml массива строк с доступными именами пользователя, паролями и уникальными для каждого имени адресами сайтов, которые передаются в следующую activity для загрузки:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name = 'Passwords'>
        <item>251100</item>
        <item>123456</item>
        <item>qwerty</item>
    </string-array>
    <string-array name = 'Sites'>
        <item>https://vseinstrumenti.ru</item>
        <item>https://chipdip.ru</item>
        <item>https://citilink.ru</item>
    </string-array>
    <string-array name = "Logins">
        <item>Petr</item>
        <item>Danil</item>
        <item>Dima</item>
    </string-array>
</resources>
```
Алгоритм нажатия кнопки выполняется в функции “LoginButton()”, которая присоединена к кнопке в XML-layout файле в качестве слушателя. В случае правильного ввода данных функция “LoginButton()” создаёт экземпляр “Intent” для перехода к “WebActivity”, помещает туда необходимые данные (имя пользователя и индекс нужного сайта в ) с помощью команды “putExtra()” и запускает новую activity функцией “startActivity()”, при неправильном вводе на экран выводится “TextView” с оповещением о неправильно введённом пароле.
```
      public void LoginButton(View view) {
        Login = LoginText.getText().toString();
        Password = PasswordText.getText().toString();
        // Проверка на совпадение с одинм из пароль/логинов
        int CountOfUsers = 3;
        String[] LoginsList = getResources().getStringArray(R.array.Logins);
        String[] PasswordsList = getResources().getStringArray(R.array.Passwords);
        int IndexOfСoincidence = -100,i;
        for (i = 0;i < CountOfUsers; i++)
            if(LoginsList[i].equals(Login))break;
        if(i < CountOfUsers)IndexOfСoincidence = i;
        if(IndexOfСoincidence == -100)
        {
            Log.i(Tag,"Неверный логин или пароль!");
            ErrorText.setVisibility(View.VISIBLE);
        }
        else
        {
            if (PasswordsList[IndexOfСoincidence].equals(Password))
            {
                ErrorText.setVisibility(View.INVISIBLE);
                Log.i(Tag, "Выполнен вход! Запускаю следующий активити");
                //Intent intent = new Intent(MainActivity.this, web.class);
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("Index", IndexOfСoincidence);
                startActivity(intent);
                ErrorText.setVisibility(View.INVISIBLE);
                LoginText.setText("");
                PasswordText.setText("");
            }
            else
            {
                Log.i(Tag, "Неверный логин или пароль!");
                ErrorText.setVisibility(View.VISIBLE);
            }
        }
    }
```
### 1.1.2 Работа с ресурсами activity
Дизайн MainActivity прописан в двух XML-layout файлах (см п.4.1 и п.4,2), хранящихся в папке “./res/layouts”. Один из них предназначен для вертикальной ориентации устройства, второй – для горизонтальной. Присоединение файлов разметки и кода приложение происходит с помощью функции “findViewById(R.id. …)”:
```	
. . .
private TextView ErrorText,LoginText,PasswordText;pet;
	. . .
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	. . .
 ErrorText = findViewById(R.id.ErrorMessage);
 LoginText = findViewById(R.id.LoginText);
 PasswordText = findViewById(R.id.PasswordText);
	. . .
	}
```
## 1.2 WebActivity
WebActivity – второе activity, на которое попадает пользователь после успешной авторизации. Интерфейс activity описан в п.3.2.
### 1.2.1 Алгоритм работы activity
Получение данных из “MainActivity” в “WebActivity” осуществляется из объекта “Intent”, созданного при переходе из первого ко второму activity, с помощью команды “getIntent().getSerializableExtra()”:
```
@Override
protected void onCreate(Bundle savedInstanceState) 
{
	. . .
	int txtName = getIntent().getIntExtra("Index", -1);
	. . .
	} 
```
Передача индекса пользователя в “шапку” приложения происходит с помощью отправки в ActionBar текста из переменной “Logins[txtName]”. Установка теста в ActionBar производится с помощью функции setTitle("DIY helper:" + Logins[txtName]);
```
@Override
protected void onCreate(Bundle savedInstanceState) 
{
	. . .
	// Принимаем Индекс и по нему вызываем нужный сайт
        	int txtName = getIntent().getIntExtra("Index", -1);
        	String Sites[] = getResources().getStringArray(R.array.Sites);
        	Log.i(Tag, Sites[txtName]);
        	String Logins[] = getResources().getStringArray(R.array.Logins);
        	setTitle("DIY helper:" + Logins[txtName]);
	. . .
	}
```
Алгоритм настройки “WebView” – встроенного браузера который загружает уникальную страницу для каждого пользователя :
```
private WebView webView;
@Override
protected void onCreate(Bundle savedInstanceState) {
…
webView = findViewById(R.id.Site);
webView.setWebViewClient(new MyWebViewClient());
wv.getSettings().setLoadsImagesAutomatically(true);
. . .
}
```
При пересоздании activity, страница, на которой находился пользователь будет сохранена и снова открыта с помощью процесса сохранения, описанного в методе “saveInstanceState()”. Если же activity была загружена впервые, будет открыта страница, которая передавалась из “MainActivity” в объекте “Intent”:
```
@Override
protected void onCreate(Bundle savedInstanceState) {
…
if (savedInstanceState==null)
{ 
    webView.loadUrl(Sites[txtName]);
    Log.i(Tag,Sites[txtName]);
} else {
    webView.loadUrl(savedInstanceState.getString("Url"));
    Log.i(Tag,CurrentUrl);
}
	. . .
}
@Override
protected void onSaveInstanceState(@NonNull Bundle outState) 
{
    super.onSaveInstanceState(outState);
	 outState.putString("Url", webView.getUrl());
 }
```
Также к “WebView” подключена возможность пролистывания страниц по нажатии кнопки “back”:
```
@Override
public void onBackPressed() 
{
	if (webView.canGoBack()) webView.goBack();
   else super.onBackPressed();
}
```
## 2 Описание интерфейса приложения
### 2.1 "MainActivity"
Интерфейс “MainActivity” демонстрируется на рисунках 1 и 3. Содержит 2 поля для ввода (EditText) логина и пароля, кнопку (Button) “Войти”, одно поле с текстом (TextView):поле ошибки(изначально невидимо) и одно ImageView поле, выполняющее функцию дизайна. Элементы activity расположены в контейнере “ConstrainLayout”.  При неправильном вводе выводится сообщение, показанное на рисунке 2. Листинг XML-файлов горизонтальной и вертикальной ориентаций находится в разделах 4.1 и 4.2.

Рисунок 1 – “MainActivity” (горизонтальная ориентация)
Рисунок 2 – “MainActivity” сообщение о неправильно введённых данных
Рисунок 3 – “MainActivity” (вертикальная ориентация)

### 2.2 “WebActivity”
Интерфейс activity демонстрируется на рисунках 4 и 5. Содержит в себе поле WebView”, веб-страница которая загружается в зависимости от имени пользователя. Элементы “WebActivity” расположены в “ConstarinLayout”. Листинг XML-файла разметки находится в разделе 4.3.

Рисунок 4 – “WebActivity” (вертикальная ориентация)
Рисунок 5 – “WebActivity” (горизонтальная ориентация)

### 2.3 Видео работы приложения
На видео демонстрируется работа всех функций программы
<a href="https://youtu.be/FpwM_aSKoUM" target="_blank"><img src="http://img.youtube.com/vi/ID_ВИДЕОРОЛИКА_НА_YOUTUBE/0.jpg" 
alt="Демонстрация работы приложения" width="240" height="180" border="10" /></a>

