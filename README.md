
1)	Добавляем в отдельном файле класс **MyFrame**. Внутри наследуемся от **JFrame**, создаем переменные-компоненты, в конструкторе описываем вид элементов окна, переопределяем метод **paint(Graphics g)** который будет рисовать содержимое матрицы.
2)	В классе **KeyboardObserver** в методе **run()** комментируем/удаляем расположенные вначале строки отвечающие за вид окна и добавление **FocusListener**(до строки **frame.addKeyListener(new KeyListener() {**).  
Строку **private JFrame frame;** перед объявлением метода **run()** меняем на **protected MyFrame frame = new MyFrame("Name");** 
3)	В классе **Room** делаем переменную метода **print()  int[][] matrix** переменной класса. Для этого объявляем переменную класса **protected int[][] matrix;** (с этого массива берем данные для отрисовки в переопределенном методе **paint()** класса **MyFrame** ). Инициализацию оставляем в методе **print()** класса **Room**.  
В классе **Room** объявляем и инициализируем переменные для хранение счета игры **public int score = 0;  static int best_score = 0;**  Переменную метода **run() keyboardObserver** также делаем переменной класса.
В методе **print()** в теле цикла вывода на экран меняем вывод элементов массива в консоль на:
```
Graphics g = keyboardObserver.frame.getGraphics();
if (g != null) keyboardObserver.frame.repaint(); 
```
После выхода из циклов выводим счет:
```
if (!snake.isAlive() && sections.size()> best_score) best_score = sections.size();
score = sections.size();        
keyboardObserver.frame.scor.setText("Score: " + score);
keyboardObserver.frame.b_scor.setText("Best Score: " + best_score);
```
4)	Тело **Main** меняем на:  
```
while (true) { //************// непрерывный цикл игры
            game = new Room(20, 20, new Snake(10, 10));
            game.snake.setDirection(SnakeDirection.DOWN);
            game.createMouse();
            game.run();
            boolean mark = false; //*************// метка для рестарта игры
            while(!mark) { //************// цикл проверки нажатия клавиши "r"
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (game.keyboardObserver.hasKeyEvents()) {
                    if ('r' == game.keyboardObserver.getEventFromTop().getKeyChar()) mark = true;
                }
            }
            game.keyboardObserver.frame.dispose(); //************// Уничтожение предыдущего окна Frame
        }
        
