package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    public TextArea text;
    public MenuItem miOFTimes;
    public MenuItem miOFArial;
    public MenuItem miOT12;
    public MenuItem miOT10;
    public MenuItem miFSortir;
    public MenuItem miECopiar;
    public MenuItem miETallar;
    public MenuItem miEPaste;
    public MenuItem miEUndo;
    public Button btCopy;
    public Button btCut;
    public Button btEng;
    public Button btUndo;
    public MenuItem miAAbout;
    public AnchorPane mainPane;

    private double fontSize;


    /**
     * S'executa al iniciar l'aplicació.
     */
    public void initialize(){
        //fontSize = text.getFont().getSize();
        btCopy.setGraphic(new ImageView("copy.png"));
        btUndo.setGraphic(new ImageView("undo.png"));
        btCut.setGraphic(new ImageView("cut.png"));
        btEng.setGraphic(new ImageView("paste.png"));



    }

    /**
     * Menú Opcions -> Font. Canvi de la font del text.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void setFont(ActionEvent actionEvent) {
        text.setFont(new Font(((MenuItem) actionEvent.getSource()).getText(), text.getFont().getSize()));
    }

    /**
     * Menú Opcions -> Tamany. Canvi del tamany de la font del text.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void setFontSize(ActionEvent actionEvent) {
        double fontSize = Double.parseDouble(((MenuItem)actionEvent.getSource()).getText());
        text.setFont(new Font(fontSize));
    }

    /**
     * Menú Fitxer -> Sortir. Surt de l'aplicació
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void sortir(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Menú Editar -> Copiar. Còpia el text seleccionat.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eCopiar(ActionEvent actionEvent) {
        text.copy();
    }

    /**
     * Menú Editar -> Tallar. Talla el text seleccionat.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eTallar(ActionEvent actionEvent) {
        text.cut();
    }

    /**
     * Menú Editar -> Enganxar. Enganxa el text del clipboard.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void ePaste(ActionEvent actionEvent) {
        text.paste();
    }

    /**
     * Menú Editar -> Desfer. Desfà la última acció.
     * @param actionEvent Event onAction de tots els MenuItem
     */
    public void eUndo(ActionEvent actionEvent) {

        text.undo();
    }

    //Metode per obrir dialeg d'informacio del programa
    public void eAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dialeg d'informació");
        alert.setHeaderText("Missatge de mostra");
        alert.setContentText("Versió: 0.5.12");

        alert.showAndWait();
    }

    //Metode per deshabilitar funcions quan no hi ha text seleccionat
    public void deshabilitar(Event event) {
        if (text.getSelectedText().equals("")){
            miECopiar.setDisable(true);
            miETallar.setDisable(true);

        }else{
            miECopiar.setDisable(true);
            miETallar.setDisable(true);
        }

    }

    //Metode per a guardar el document
    public void guardar(ActionEvent actionEvent) {

        Stage primaryStage = ((Stage)mainPane.getScene().getWindow());


        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(primaryStage);

        if(file!=null) {
            FileWriter fw = null;
            BufferedWriter bw = null;

            try {

                fw = new FileWriter(file, false);
                bw = new BufferedWriter(fw);

                String texto = text.getText();
                bw.write(texto, 0, texto.length());
                primaryStage.setTitle(file.getName());
            } catch (Exception e) {

                text.appendText(e.toString());

            } finally {

                try {

                    bw.close();

                } catch (Exception e2) {

                    text.appendText(e2.toString());

                }
            }
        }
    }

    //Metode per obrir un document ja existent
    public void obrir(ActionEvent actionEvent) {

        StringBuilder strB = new StringBuilder();
        BufferedReader bufferedReader = null;


        Stage primaryStage = ((Stage)mainPane.getScene().getWindow());
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        primaryStage.setTitle(file.getName());

        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;

            while ((text = bufferedReader.readLine()) != null) {

                strB.append(text+"\n");
            }
            
        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            text.setText(String.valueOf(strB));
        }

    }
}
