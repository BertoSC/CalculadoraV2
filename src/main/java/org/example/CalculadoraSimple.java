package org.example;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CalculadoraSimple {
    private int primerNum;
    private int segundoNum;
    String calculo;
    String operacionTipo;
    Boolean operacionNueva;

    public CalculadoraSimple(){
        this.primerNum = 0;
        this.segundoNum = 0;
        this.calculo = "";
        this.operacionTipo= "";
        this.operacionNueva = false;
        crearCalculadora();
    }
    // c
    public void crearCalculadora () {

        // Creación de ventana

        JFrame ventana = new JFrame("Calculadora simple");
        ventana.setSize(350,400);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.setBackground(Color.DARK_GRAY);
        ventana.setResizable(false);

        // Creación de pantalla

        JTextField pantalla = new JTextField();
        pantalla.setPreferredSize(new Dimension(500, 75));
        pantalla.setBackground(Color.DARK_GRAY);
        pantalla.setBorder(new EmptyBorder(10, 10, 10, 10));

        try {
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/digital-7.ttf")).deriveFont(25f);
            pantalla.setFont(digitalFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            pantalla.setFont(new Font("SansSerif", Font.PLAIN, 15));
        }

        pantalla.setForeground(Color.YELLOW);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);

        // Creación de botones

        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(Color.DARK_GRAY);
        botonesPanel.setLayout(new GridLayout(4, 4));

        JButton boton1 = crearBoton(botonesPanel, "1");
        agregarAccionNumero(boton1, pantalla);

        JButton boton2 = crearBoton(botonesPanel, "2");
        agregarAccionNumero(boton2, pantalla);

        JButton boton3 = crearBoton(botonesPanel, "3");
        agregarAccionNumero(boton3, pantalla);

        JButton botonMas = crearBoton(botonesPanel, "+");
        agregarAccionGeneral(botonMas, pantalla);

        JButton boton4 = crearBoton(botonesPanel, "4");
        agregarAccionNumero(boton4, pantalla);

        JButton boton5 = crearBoton(botonesPanel, "5");
        agregarAccionNumero(boton5, pantalla);

        JButton boton6 = crearBoton(botonesPanel, "6");
        agregarAccionNumero(boton6, pantalla);

        JButton botonMenos = crearBoton(botonesPanel, "-");
        agregarAccionGeneral(botonMenos, pantalla);

        JButton boton7 = crearBoton(botonesPanel, "7");
        agregarAccionNumero(boton7, pantalla);

        JButton boton8 = crearBoton(botonesPanel, "8");
        agregarAccionNumero(boton8, pantalla);

        JButton boton9 = crearBoton(botonesPanel, "9");
        agregarAccionNumero(boton9, pantalla);

        JButton botonMult= crearBoton(botonesPanel, "*");
        agregarAccionGeneral(botonMult, pantalla);

        JButton botonResetear = crearBoton(botonesPanel, "C");
        agregarAccionGeneral(botonResetear, pantalla);

        JButton boton0 = crearBoton(botonesPanel, "0");
        agregarAccionNumero(boton0, pantalla);

        JButton botonIgual = crearBoton(botonesPanel, "=");
        agregarAccionGeneral(botonIgual, pantalla);

        JButton botonDiv = crearBoton(botonesPanel, "/");
        agregarAccionGeneral(botonDiv,pantalla);

        ventana.add(pantalla, BorderLayout.NORTH);
        ventana.add(botonesPanel, BorderLayout.CENTER);
        ventana.setVisible(true);
    }

    // Método para crear botón y darle formato

    private JButton crearBoton(JPanel panel, String texto) {
        JButton boton = new JButton(texto);

        Font latoFont = null;
        try {
            latoFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Lato-Regular.ttf")).deriveFont(15f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            latoFont = new Font("SansSerif", Font.PLAIN, 18);
        }

        boton.setFont(latoFont);
        boton.setBackground(Color.LIGHT_GRAY);

        panel.add(boton);
        return boton;
    }

    // Método para agregar una acción a los botones de números

    private void agregarAccionNumero (JButton but, JTextField pant){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operacionNueva){
                    pant.setText("");
                    operacionNueva=false;
                }
                String seleccion = e.getActionCommand();
                pant.setText(pant.getText()+seleccion);
            }
        });
    }

    // método para dar acciones a los botones de operaciones y control de pantalla

    private void agregarAccionGeneral(JButton but, JTextField pant) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accion = e.getActionCommand();
                try {
                    if (accion.equals("+") || accion.equals("-") || accion.equals("*") || accion.equals("/")) {

                        if (!operacionTipo.isEmpty() && !pant.getText().isEmpty() && !operacionNueva) {
                            segundoNum = Integer.parseInt(pant.getText());
                            realizarOperación(pant);
                            primerNum = Integer.parseInt(pant.getText());
                        } else {
                            if (!pant.getText().isEmpty()) {
                                primerNum = Integer.parseInt(pant.getText());
                            }
                        }

                        operacionTipo = accion;
                        operacionNueva = true;

                    } else if (accion.equals("C")) {
                        primerNum = 0;
                        segundoNum = 0;
                        operacionTipo = "";
                        pant.setText("");

                    } else if (accion.equals("=")) {
                        if (!pant.getText().isEmpty() && !operacionTipo.isEmpty() && !operacionNueva) {
                            segundoNum = Integer.parseInt(pant.getText());
                            realizarOperación(pant);
                            operacionNueva = true;
                            operacionTipo = "";
                        }
                    }

                } catch (NumberFormatException n) {
                    pant.setText("ERROR");
                    operacionNueva = true;
                }
            }
        });
    }


    // método para hacer los cálculos matemáticos

    private void realizarOperación(JTextField pant){
        switch (operacionTipo){
            case "+":
                pant.setText(String.valueOf(primerNum+segundoNum));
                break;

            case "-":
                pant.setText(String.valueOf(primerNum-segundoNum));
                break;

            case "*":
                pant.setText(String.valueOf(primerNum*segundoNum));
                break;

            case "/":
                if (segundoNum==0){
                    pant.setText("ERROR");
                    break;
                } else {

                    pant.setText(String.valueOf(primerNum / segundoNum));
                    break;
                }
        }
    }

    public static void main(String[] args) {
        new CalculadoraSimple();
    }
}