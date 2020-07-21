package MainWindow;

import Student.Student;
import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTreeTableColumn<Student,String> jfxTreeTableColumnName;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField hiddenTextField;


    public void onChange() {
        StringProperty data = new SimpleStringProperty();

        data.bind(hiddenTextField.textProperty());
        String dataToParse = new String("");
        dataToParse = data.toString();
        System.out.println("This is response string: " + data.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getSerialPort
        SerialPort sp = SerialPort.getCommPort("COM4");
        sp.setComPortParameters(9600,8,1,0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING,0,0);

        if(sp.openPort()) {
            /*
            * Do some fuckin stuff, begin binding hiddenTextField Changing*/
            hiddenTextField.setText(hexToAscii("4A"));

        } else {
            System.out.println("Some thing went wrong");
        }

//        while(true) {
//
//            hiddenTextField.setText(sc.nextLine());
//        }
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
