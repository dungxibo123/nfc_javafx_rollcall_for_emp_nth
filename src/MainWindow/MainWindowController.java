package MainWindow;

import Student.Student;
import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private TableView<Student> mainTableView;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private TableColumn<Student,String> jfxTreeTableColumnName;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane subAnchorPane;
    @FXML
    private TextField hiddenTextField;
    @FXML
    private Button clickTorRead;

    public void onChange() {
        StringProperty data = new SimpleStringProperty();

        data.bind(hiddenTextField.textProperty());
        String dataToParse = new String("");
        dataToParse = data.toString();
        System.out.println("This is response string: " + data.toString());
    }

    private void readCard() {
        System.out.println("du");
        SerialPort sp = SerialPort.getCommPort("COM4");
        System.out.println(sp);
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        if (sp.openPort()) {
            System.out.println("oke");
        } else {
            System.out.println("No oke");
            return;
        }
        byte[] res = new byte[2048];
        try {
            for (; ; ) {
                sp.getInputStream().read(res);
                System.out.println(Arrays.toString(res));
                System.out.println("end");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainTableView.prefHeightProperty().bind(anchorPane.heightProperty());
        mainTableView.prefWidthProperty().bind(anchorPane.widthProperty());
        jfxTreeTableColumnName.prefWidthProperty().bind(mainTableView.widthProperty());
        jfxTreeTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    //Hướng 1:
    // Tạo 2 thread chia cùng 1 biến studentInformation
    // Thread1 sẽ xử lý dữ liệu
    // Thread2 sẽ work
    /*
    * Ưu:
    * - Chưa thấy ưu điểm
    * Khuyết:
    * Dễ bị conflict và xảy ra nhiều lỗi mà amateur như em chưa từng gặp
    *
    *
    *
    * Hướng 2:
    * - Sử dụng một uneditable TextField. Dùng hàm scan, mỗi lần scan thấy một cái sẽ xử thay đổi text trên TextField
    * - Binding TextField Change. Nếu đổi thì assign newValue cho biến studentInformation.
    *
    * Ưu:
    * - Dễ, code ít lỗi, có thể tham khảo code mẫu
    * Khuyết:
    * - Chưa thấy khuyết điểm
    * */




}
