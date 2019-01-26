package com.javarush.task.task23.task2312;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Light on 24.01.2019.
 */
public class MyFrame extends JFrame {
    JPanel main_p = new JPanel(); //Главная панель
    JPanel up_p = new JPanel(); //панель отображения счета игры
    Label scor = new Label(); //Поле для вывода текущего счета
    Label b_scor = new Label(); // поле для вывода лучшего счета

    public MyFrame(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(420, 500);
        setResizable(false);
        setVisible(true);
        main_p.setBackground(new Color(0,120,60)); //Цвет Фона игрового поля
        main_p.setSize(400,400);
        up_p.setBackground(Color.GRAY); //Цвет фона панели вывода счета
        up_p.setSize(420,60);
        up_p.setLayout(new GridLayout());
        up_p.add(scor);
        scor.setFont(new Font("TimesRoman", Font.BOLD, 18));
        b_scor.setFont(new Font("TimesRoman", Font.BOLD, 18));
        b_scor.setText("Best Score: 0");
        up_p.add(b_scor);
        add(up_p);
        add(main_p);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int x,y;
        // Сдвиг чтобы элементы игры не прятались за рамки окна
        int shift_y = 90; //Сдвиг игрового поля по вертикали
        int shift_x = 9; //Сдвиг игрового поля по горизонтали
        int[][] mt = Room.game.matrix; // Массив с числовыми представлениями элементов игрового поля
        //Рисуем игровое поле
        g.setColor(new Color(0,100,0));
        g.fillRect(shift_x,shift_y,400,400);
        g.setColor(Color.BLACK);
        g.drawRect(shift_x,shift_y,400,400);

        //Рисуем элементы игры. Поле игры представлено в виде воображаемой сетки с шагом 20 пикселей
        //В ячейках этой сетки рисуем змею и мышей
        for (int i = 0; i < mt.length; i++) {
            for (int j = 0; j < mt[0].length; j++) {
                //координаты верхнего левого угла ячейки
                x = j*20+shift_x;
                y = i*20+shift_y;
                //Голова змеи
                g.setColor(Color.BLACK);
                if (mt[i][j] == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x, y, 20, 20);
                }
                //Тело змеи
                if (mt[i][j] == 1) {
                    g.setColor(Color.MAGENTA);
                    g.fillOval(x + 2, y + 2, 18, 18);
                }
                //Мышка
                if (mt[i][j] == 3) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x+4, y+4, 12, 12);
                    g.setColor(Color.YELLOW);
                    g.fillOval(x+5, y+5, 10, 10);
                }
                //Конец жизни змеи
                if (mt[i][j] == 4) {
                    g.fillRect(x + 6, y + 6, 8, 8);
                    g.drawLine(x,y,x+20,y+20);
                    g.drawLine(x+20,y,x,y+20);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 36));
                    g.drawString("GAME OVER!!!",shift_x+80, shift_y+200);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g.drawString("Press 'r' to restart",shift_x+110, shift_y+250);
                }

            }
        }
    }
}
