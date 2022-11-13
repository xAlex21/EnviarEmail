package enviar.email;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EmailController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private TextField servidor;

	@FXML
	private TextField puerto;

	@FXML
	private TextField remitente;

	@FXML
	private TextField contraseña;

	@FXML
	private TextField destinatario;

	@FXML
	private TextField asunto;

	@FXML
	private TextArea mensaje;

	@FXML
	private CheckBox ssl;

	public EmailController() throws IOException {

		URL fxml = getClass().getResource("/fxml/View.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxml);
		fxmlLoader.setController(this);
		fxmlLoader.load();

	}

	@FXML
	void enviar(ActionEvent event) {
		comprobarValoresNulos();
	}

	@FXML
	void vaciar(ActionEvent event) {
		servidor.setText(null);
		puerto.setText(null);
		remitente.setText(null);
		contraseña.setText(null);
		destinatario.setText(null);
		asunto.setText(null);
		mensaje.setText(null);
	}

	@FXML
	void cerrar(ActionEvent event) {
		System.exit(0);
	}

	private void comprobarValoresNulos() {

		boolean correosCorrectos = false;

		if ((servidor.getText().isEmpty()) || (puerto.getText().isEmpty()) || (remitente.getText().isEmpty())
				|| (contraseña.getText().isEmpty()) || (destinatario.getText().isEmpty())
				|| (asunto.getText().isEmpty()) || (mensaje.getText().isEmpty())) {

			String texto = "Debe rellenar todos los datos.";
			mostrarAlertaError(texto);
		} else {

			if (comprobarCorreo() && comprobarPuertos()) {
				System.out.println("-------------------Enviar Correo------------------");
				enviarCorreo();
			}

		}
	}

	private void enviarCorreo() {
		boolean checked;
		
		if(ssl.isSelected()) {
			checked = true;
		}else {
			checked = false;
		}
		
		Email email = new SimpleEmail();
		email.setHostName(servidor.getText());
		email.setSmtpPort(Integer.parseInt(puerto.getText()));
		email.setAuthenticator(new DefaultAuthenticator(remitente.getText(), contraseña.getText()));
		email.setSSLOnConnect(checked);
		try {
			email.setFrom(remitente.getText());
			email.setSubject(asunto.getText());
			email.setMsg(mensaje.getText());
			email.addTo(destinatario.getText());
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean comprobarCorreo() {

		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		if (pattern.matcher(remitente.getText()).find() == false
				|| pattern.matcher(destinatario.getText()).find() == false) {

			String texto = "El email introducido no es correcto";
			mostrarAlertaError(texto);
			return false;

		} else {
			return true;
		}

	}

	private boolean comprobarPuertos() {
		int puertoInt;

		try {
			puertoInt = Integer.parseInt(puerto.getText());
			return true;
		} catch (NumberFormatException e) {
			mostrarAlertaError("El puerto debe de ser de valor numerico");
			return false;
		}
	}

	private void mostrarAlertaError(String texto) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se ha podido enviar el email.");
		alert.setContentText(texto);
		alert.showAndWait();
	}

	public GridPane getView() {
		return view;
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
