package com.javarush.task.task23.task2312;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Основной класс программы.
 */
public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    protected int[][] matrix;
    protected int score = 0;  //*************// счет
    protected static int best_score = 0;  //*****************// лучший счет
    private KeyboardObserver keyboardObserver;


    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        //System.out.println("Game Over!");
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
        //Создаем массив, куда будем "рисовать" текущее состояние игры
        matrix = new int[height][width];

        //Рисуем все кусочки змеи
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        //Рисуем голову змеи (4 - если змея мертвая)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        //Рисуем мышь
        matrix[mouse.getY()][mouse.getX()] = 3;

        //Выводим все это на экран
        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Graphics g = keyboardObserver.frame.getGraphics(); //********************//
                if (g != null) keyboardObserver.frame.repaint();   //***************// Прорисовка матрицы в JFrame paint()
            }
            //System.out.println();
        }
        if (!snake.isAlive() && sections.size()> best_score) best_score = sections.size();
        score = sections.size(); //************//
        keyboardObserver.frame.scor.setText("Score: " + score); //*****************//
        keyboardObserver.frame.b_scor.setText("Best Score: " + best_score); //*************//

        //System.out.println();
        //System.out.println();
        //System.out.println();
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public static Room game;

    public static void main(String[] args) {
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
    }

    private int initialDelay = 400;
    private int delayStep = 10;

    /**
     * Программа делает паузу, длинна которой зависит от длинны змеи.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            //int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            int delay = (initialDelay - delayStep * level);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
