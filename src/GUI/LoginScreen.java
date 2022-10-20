package GUI;

import Database.ConnectionDB;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginScreen extends JFrame {

    //declaring components
    private JPanel panel;
    private JLabel lb_title;
    private JLabel lb_username;
    private JLabel lb_password;
    private static JTextField tf_username;
    private JPasswordField pf_password;
    private JButton btn_login;
    private JButton btn_cancel;
    private JCheckBox cb_showPassword;
    public static String userLoggedIn;
    
    public LoginScreen(){
        super();
        this.setSize(400,350);
        this.setTitle("Login screen");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        initComponents();
        initListeners();
        this.setVisible(true);
    }

    private void initComponents() {
        panel = new JPanel();

        //setting a layout for the window
        SpringLayout sl = new SpringLayout();
        panel.setLayout(sl);

        Border border = BorderFactory.createEtchedBorder();
        Font f = new Font("Arial", 1, 12);

        //initializing components
        lb_title = new JLabel("Money Counter");
        lb_title.setPreferredSize(new Dimension(143,30));
        lb_title.setFont(new Font("Arial", Font.PLAIN, 20));
        lb_title.setBorder(border);

        lb_username = new JLabel("Korisničko ime:");
        lb_username.setFont(f);

        lb_password = new JLabel("Lozinka:");
        lb_password.setFont(f);

        tf_username = new JTextField();
        tf_username.setPreferredSize(new Dimension(130,25));

        pf_password = new JPasswordField();
        pf_password.setPreferredSize(new Dimension(130,25));

        cb_showPassword = new JCheckBox("Prikaži lozinku");
        cb_showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb_showPassword.isSelected()){
                    pf_password.setEchoChar((char) 0);
                }else
                {
                    pf_password.setEchoChar('\u2022');
                }
            }
        });


        btn_login = new JButton("Potvrdi");
        btn_login.setPreferredSize(new Dimension(100,50));

        btn_cancel = new JButton("Poništi");
        btn_cancel.setPreferredSize(new Dimension(100,50));

        //setting the placement of components
        sl.putConstraint(SpringLayout.WEST, lb_title, 120, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, lb_title, 10, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, lb_username, 60, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, lb_username, 90, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, tf_username, 160, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, tf_username, 85, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, lb_password, 101, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, lb_password, 130, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, pf_password, 160, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, pf_password, 125, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, cb_showPassword, 160, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, cb_showPassword, 150, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, btn_login, 70, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, btn_login, 215, SpringLayout.NORTH, panel);

        sl.putConstraint(SpringLayout.WEST, btn_cancel, 190, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.NORTH, btn_cancel, 215, SpringLayout.NORTH, panel);

        //adding components to JPanel
        panel.add(lb_title);
        panel.add(lb_username);
        panel.add(lb_password);
        panel.add(tf_username);
        panel.add(pf_password);
        panel.add(btn_login);
        panel.add(btn_cancel);
        panel.add(cb_showPassword);

        setContentPane(panel);
    }

    public static String getUser(){
        return tf_username.getText();
    }

    private void initListeners() {

        btn_login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String userName = tf_username.getText();
                String password = String.valueOf(pf_password.getPassword());

                try{
                    Statement stm = ConnectionDB.conn.createStatement();
                    String statement = "SELECT username, password FROM users where username='"+userName+"' and password='"+password+"'";

                    ResultSet rs = stm.executeQuery(statement);

                    if (rs.next()) {
                        dispose();
                        new ChooseCounter();
                    }else{
                            JOptionPane.showMessageDialog(null, "Pogrešno korisničko ime i/ili lozinka", "Greška!", JOptionPane.ERROR_MESSAGE);
                            tf_username.setText("");
                            pf_password.setText("");
                        }

                    rs.close();

                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });

        btn_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tf_username.setText("");
                pf_password.setText("");
            }
        });
    }



}