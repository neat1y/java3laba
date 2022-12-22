package qq;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame extends JFrame{
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    // Массив коэффициентов многочлена
    private Double[] coefficients;
    // Объект диалогового окна для выбора файлов
// Компонент не создаётся изначально, т.к. может и не понадобиться
// пользователю если тот не собирается сохранять данные в файл
    private JFileChooser fileChooser = null;
    // Элементы меню вынесены в поля данных класса, так как ими необходимо
// манипулировать из разных мест
    private JMenuItem saveToTextMenuItem;
    private JMenuItem searchValueMenuItem;

    // Поля ввода для считывания значений переменных
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    // Визуализатор ячеек таблицы
    private GornerTableCellRenderer renderer = new
            GornerTableCellRenderer();
    // Модель данных с результатами вычислений
    private GornerTableModel data;
    public MainFrame(Double[] coefficients) {
// Обязательный вызов конструктора предка
        super("Табулирование многочлена на отрезке по схеме Горнера");
// Запомнить во внутреннем поле переданные коэффициенты
        this.coefficients = coefficients;
// Установить размеры окна
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
// Создать меню
        JMenuBar menuBar = new JMenuBar();
// Установить меню в качестве главного меню приложения
        setJMenuBar(menuBar);
// Добавить в меню пункт меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        JMenu menuspravka = new JMenu("справка");
// Добавить его в главное меню
        menuBar.add(fileMenu);
// Создать пункт меню "Таблица"
        JMenu tableMenu = new JMenu("Таблица");
// Добавить его в главное меню
        menuBar.add(tableMenu);

        menuBar.add(menuspravka);
        Action name=new AbstractAction("name") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame NEW=new JFrame();
                NEW.setSize(100,100);
                JLabel NAME=new JLabel("антон");
                Box name=Box.createHorizontalBox();
                Box.createHorizontalGlue();
                name.add(Box.createHorizontalStrut(50));
                name.add(NAME);
                NEW.add(name);
                NEW.setVisible(true);
            }
        };
        menuspravka.add(name);
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
        public void actionPerformed(ActionEvent event) {
            if (fileChooser==null) {
// Если экземпляр диалогового окна "Открыть

// то создать его
                fileChooser = new JFileChooser();
// и инициализировать текущей директорией
                fileChooser.setCurrentDirectory(new File("."));
            }
// Показать диалоговое окно
            if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
// Если результат его показа успешный,
// сохранить данные в текстовый файл
                saveToTextFile(fileChooser.getSelectedFile());
        }
    };
// Добавить соответствующий пункт подменю в меню "Файл"
    saveToTextMenuItem = fileMenu.add(saveToTextAction);
// По умолчанию пункт меню является недоступным (данных ещѐ нет)
saveToTextMenuItem.setEnabled(false);
    // Создать новое "действие" по сохранению в текстовый файл

// Добавить соответствующий пункт подменю в меню "Файл"
// По умолчанию пункт меню является недоступным(данных ещѐ нет)

// Создать новое действие по поиску значений многочлена
        Action diopozon = new AbstractAction("диапозон") {
            public void actionPerformed(ActionEvent event) {
// Запросить пользователя ввести искомую строку
                JFrame table=new JFrame();
                table.setSize(150,120);
                Box privet=Box.createHorizontalBox();
                JLabel go=new JLabel("to");
                JLabel from=new JLabel("from");
                JTextField firstx=new JTextField("0.0",20);
                JTextField secondx=new JTextField("0.0",20);
                Box.createHorizontalGlue();
                privet.add(go);
                privet.add(firstx);
                Box privet1=Box.createHorizontalBox();
                privet.add(Box.createHorizontalStrut(10));
                privet.add(from);
                privet.add(secondx);
                privet.add(Box.createHorizontalStrut(10));
                privet.createHorizontalGlue();
                Double x = Double.parseDouble(firstx.getText());
                Double y = Double.parseDouble(secondx.getText());

                JButton vich=new JButton("вычислит");
                vich.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        try {
                            Double x = Double.parseDouble(firstx.getText());
                            Double y = Double.parseDouble(secondx.getText());
                            renderer.setNeedle(x,y);

                            getContentPane().repaint();
                            table.dispose();

                        } catch (NumberFormatException  ex) {
                            JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                privet.add(Box.createHorizontalStrut(30));

                privet1.add(vich);
                Box kekw=Box.createVerticalBox();
                kekw.add(privet);
                kekw.add(privet1);
                table.add(kekw);
                table.setVisible(true);

            }
        };
    searchValueMenuItem = tableMenu.add(diopozon);
// По умолчанию пункт меню является недоступным (данных ещё нет)
searchValueMenuItem.setEnabled(false);
    // Создать область с полями ввода для границ отрезка и шага
// Создать подпись для ввода левой границы отрезка
    JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
// Создать текстовое поле для ввода значения длиной в 10 символов
// со значением по умолчанию 0.0
    textFieldFrom = new JTextField("0.0", 10);
// Установить максимальный размер равный предпочтительному, чтобы
// предотвратить увеличение размера поля ввода
textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
    // Создать подпись для ввода левой границы отрезка
    JLabel labelForTo = new JLabel("до:");
// Создать текстовое поле для ввода значения длиной в 10 символов
// со значением по умолчанию 1.0
    textFieldTo = new JTextField("1.0", 10);
// Установить максимальный размер равный предпочтительному, чтобы
// предотвратить увеличение размера поля ввода
textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
    // Создать подпись для ввода шага табулирования
    JLabel labelForStep = new JLabel("с шагом:");
// Создать текстовое поле для ввода значения длиной в 10 символов
// со значением по умолчанию 1.0
    textFieldStep = new JTextField("0.1", 10);
// Установить максимальный размер равный предпочтительному, чтобы
// предотвратить увеличение размера поля ввода
textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());
    // Создать контейнер 1 типа "коробка с горизонтальной укладкой"
    Box hboxRange = Box.createHorizontalBox();
// Задать для контейнера тип рамки "объѐмная"
hboxRange.setBorder(BorderFactory.createBevelBorder(1));
// Добавить "клей" C1-H1
hboxRange.add(Box.createHorizontalGlue());
// Добавить подпись "От"
hboxRange.add(labelForFrom);
// Добавить "распорку" C1-H2
hboxRange.add(Box.createHorizontalStrut(10));
// Добавить поле ввода "От"
hboxRange.add(textFieldFrom);
// Добавить "распорку" C1-H3
hboxRange.add(Box.createHorizontalStrut(20));
// Добавить подпись "До"
hboxRange.add(labelForTo);
// Добавить "распорку" C1-H4
hboxRange.add(Box.createHorizontalStrut(10));
// Добавить поле ввода "До"
hboxRange.add(textFieldTo);
// Добавить "распорку" C1-H5
hboxRange.add(Box.createHorizontalStrut(20));
// Добавить подпись "с шагом"
hboxRange.add(labelForStep);
// Добавить "распорку" C1-H6
hboxRange.add(Box.createHorizontalStrut(10));
// Добавить поле для ввода шага табулирования
hboxRange.add(textFieldStep);
// Добавить "клей" C1-H7
hboxRange.add(Box.createHorizontalGlue());
// Установить предпочтительный размер области равным удвоенному
// минимальному, чтобы при компоновке область совсем не сдавили
hboxRange.setPreferredSize(new Dimension(new Double(hboxRange.getMaximumSize().getWidth()).intValue(),
new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
    // Установить область в верхнюю (северную) часть компоновки
    getContentPane().add(hboxRange, BorderLayout.NORTH);
    // Создать кнопку "Вычислить"
    JButton buttonCalc = new JButton("Вычислить");
// Задать действие на нажатие "Вычислить" и привязать к кнопке
buttonCalc.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            try {
// Считать значения начала и конца отрезка, шага
                Double from = Double.parseDouble(textFieldFrom.getText());
                Double to =Double.parseDouble(textFieldTo.getText());
                Double step = Double.parseDouble(textFieldStep.getText());
// На основе считанных данных создать новый

                        data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
// Создать новый экземпляр таблицы
                JTable table = new JTable(data);
// Установить в качестве визуализатора ячеек для

                table.setDefaultRenderer(Double.class, renderer);
// Установить размер строки таблицы в 30

                table.setRowHeight(30);
// Удалить все вложенные элементы из контейнера

                hBoxResult.removeAll();
// Добавить в hBoxResult таблицу, "обѐрнутую" в

                hBoxResult.add(new JScrollPane(table));
// Обновить область содержания главного окна
                getContentPane().validate();
// Пометить ряд элементов меню как доступных
                saveToTextMenuItem.setEnabled(true);

                searchValueMenuItem.setEnabled(true);
            } catch (NumberFormatException ex) {
// В случае ошибки преобразования чисел показать

                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    });
    // Создать кнопку "Очистить поля"
    JButton buttonReset = new JButton("Очистить поля");
// Задать действие на нажатие "Очистить поля" и привязать к кнопке
        buttonReset.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Установить в полях ввода значения по умолчанию
            textFieldFrom.setText("0.0");
            textFieldTo.setText("1.0");
            textFieldStep.setText("0.1");
            hBoxResult.removeAll();
// Добавить в контейнер пустую панель
            hBoxResult.add(new JPanel());
// Пометить элементы меню как недоступные

            saveToTextMenuItem.setEnabled(false);
            searchValueMenuItem.setEnabled(false);
// Обновить область содержания главного окна
            getContentPane().validate();
// Обновить область содержания главного
        }
    });
    // Поместить созданные кнопки в контейнер
    Box hboxButtons = Box.createHorizontalBox();
hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
hboxButtons.add(Box.createHorizontalGlue());
hboxButtons.add(buttonCalc);
hboxButtons.add(Box.createHorizontalStrut(30));
hboxButtons.add(buttonReset);
hboxButtons.add(Box.createHorizontalGlue());
// Установить предпочтительный размер области равным удвоенному

// компоновке окна область совсем не сдавили
hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new
    Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2));
    // Разместить контейнер с кнопками в нижней (южной) области
    getContentPane().add(hboxButtons, BorderLayout.SOUTH);
// Область для вывода результата пока что пустая
    hBoxResult = Box.createHorizontalBox();
hBoxResult.add(new JPanel());
    // Установить контейнер hBoxResult в главной (центральной) области

    getContentPane().add(hBoxResult, BorderLayout.CENTER);
}

    protected void saveToTextFile(File selectedFile) {
        try {
// Создать новый символьный поток вывода, направленный в

            PrintStream out = new PrintStream(selectedFile);
// Записать в поток вывода заголовочные сведения

// Записать в поток вывода значения в точках
            for (int i = 0; i<data.getRowCount(); i++) {
                out.println(  data.getValueAt(i,0)+" " +data.getValueAt(i,1));

            }

// Закрыть поток
            out.close();
        } catch (FileNotFoundException e) {
// Исключительную ситуацию "ФайлНеНайден" можно не
// обрабатывать, так как мы файл создаѐм, а не открываем
        }
    }

    public static void main(String[] args) {
// Если не задано ни одного аргумента командной строки -
// Продолжать вычисления невозможно, коэффиценты неизвестны
        if (args.length==0) {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
// Перебрать аргументы, пытаясь преобразовать их в Double
            for (String arg: args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        }
        catch (NumberFormatException ex) {

            System.out.println("Ошибка преобразования строки '" +
                    args[i] + "' в число типа Double");
            System.exit(-2);
        }
// Создать экземпляр главного окна, передав ему коэффициенты
        MainFrame frame = new MainFrame(coefficients);
// Задать действие, выполняемое при закрытии окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
