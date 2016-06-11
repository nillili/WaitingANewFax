/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waitingnewfile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import static waitingnewfile.WaitingNewFile.primaryStage;

/**
 *
 * @author jinihong
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private Parent root;

    @FXML
    private TableColumn<FileInfoBean, String> c_name;

    @FXML
    private TableColumn<FileInfoBean, String> c_date;

    @FXML
    private TableView<FileInfoBean> table; // Value injected by FXMLLoader

    @FXML
    private Label label;

    @FXML
    private TextField dir_loc;

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    private void saveLocInfo()
    {

        String dir_loc1 = this.dir_loc.getText();
        if (dir_loc1 != null && dir_loc1.length() > 0 && !dir_loc1.equals(CmnUtil.getPath()))
        {
            File file_loc = new File(dir_loc1);

            if (file_loc.isDirectory())
            {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("저장 하였 습니다.");
                alert.setContentText("다시 실행 하세요.");
                alert.showAndWait();

                (new CmnUtil()).saveFilesInfo(CmnUtil.STR_DIR_PATH, dir_loc1);
            } else
            {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("입력값이 정상이 아닙니다");
                alert.setContentText("다시 입력 해 주세요");
                alert.showAndWait();

            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.c_name.setCellValueFactory(new PropertyValueFactory<FileInfoBean, String>("name"));
        this.c_date.setCellValueFactory(new PropertyValueFactory<FileInfoBean, String>("update_dt"));

        String dir_loc = (String) (new CmnUtil()).readFilesInfo(CmnUtil.STR_DIR_PATH);
        this.dir_loc.setText(dir_loc);

        table_content = FXCollections.observableArrayList();

        table.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                if (t.getClickCount() == 2)
                {

                    List<FileInfoBean> list = table.getSelectionModel().getSelectedItems();
                    if (list.size() == 1)
                    {
                        FileInfoBean cb = list.get(0);

                        (new CmnUtil()).putParam(cb.getName());
                        (new FXMLDocumentController()).showPopup(cb.getName());

                        // 배경색을 없애기 위해 reflash
                        table.getSelectionModel().getSelectedItem().setNew_file(false);
                        table.getColumns().get(0).setVisible(false);
                        table.getColumns().get(0).setVisible(true);

                    }

                }
            }
        });

        table.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(final KeyEvent keyEvent)
            {
                final FileInfoBean selectedItem = table.getSelectionModel().getSelectedItem();

                if (selectedItem != null)
                {
                    if (keyEvent.getCode().equals(KeyCode.DELETE))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
                        alert.setTitle("삭제");
                        alert.setHeaderText(null);
                        alert.setContentText("정말 삭제 할까요?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.YES)
                        {
                            File rm_file = new File((new CmnUtil()).getPath(), selectedItem.getName());

                            File sameFileName = new File((new CmnUtil()).getPath(), selectedItem.getName());
                                                        
                            if (rm_file.delete())
                            {
                                table.getItems().remove(selectedItem);
                            }
                        }

                    } else if (keyEvent.getCode().equals(KeyCode.ENTER))
                    {
                        List<FileInfoBean> list = table.getSelectionModel().getSelectedItems();
                        if (list.size() == 1)
                        {
                            FileInfoBean cb = list.get(0);

                            (new CmnUtil()).putParam(cb.getName());
                            (new FXMLDocumentController()).showPopup(cb.getName());

                            // 배경색을 없애기 위해 reflash
                            table.getSelectionModel().getSelectedItem().setNew_file(false);
                            table.getColumns().get(0).setVisible(false);
                            table.getColumns().get(0).setVisible(true);

                        }

                    }
                }
            }
        });

        this.table.setRowFactory(new Callback<TableView<FileInfoBean>, TableRow<FileInfoBean>>()
        {
            @Override
            public TableRow call(final TableView<FileInfoBean> p)
            {
                return new TableRow<FileInfoBean>()
                {

                    @Override
                    protected void updateItem(FileInfoBean item, boolean empty)
                    {

                        super.updateItem(item, empty);
                        if (item == null)
                        {
                            setTooltip(null);
                        } else
                        {
                            if (item.isNew_file())
                            {
                                this.setStyle("-fx-background-color:red");
                            } else
                            {
                                this.setStyle("");  // 이거 중요
                            }
                        }
                    }
                };
            }
        });
    }
    ObservableList<FileInfoBean> table_content;

    public void addRecord(String a_name, String a_date, boolean is_new)
    {
        FileInfoBean fib = new FileInfoBean();
        fib.setName(a_name);
        fib.setUpdate_dt(a_date);
        fib.setNew_file(is_new);

        table_content.add(0, fib);
        table.setItems(table_content);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.show();
        stage.toFront();
    }

    public void showPopup(String file_name)
    {
        try
        {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("PdfViewer.fxml"));

            BorderPane page = (BorderPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(file_name);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(WaitingNewFile.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
