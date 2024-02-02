import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frame extends JFrame implements ActionListener {
    public sql_connect sql;
    private JButton memberManagementButton;
    private JButton bookManagementButton;
    private JButton borrowManagementButton;
    private JButton c_selectButton;
    private JButton r_deleteButton;
    private JTextField r_delete_TextField;
    private JButton modifyButton;
    private JButton insertButton;
    private JButton delete_btn;
    private JButton search_btn;
    private JButton lateButton;
    private JButton c_search_btn;
    private JButton b_search_btn;
    private JTextField delete_TextField;
    private JTextField search_TextField;
    private JTextField c_search_TextField;
    private JTextField b_search_TextField;
    private JButton b_selectButton;
    private JButton r_selectButton;
    private JButton check_ID_btn;
    private JButton modify_btn;
    private JButton insert_btn;
    public JFrame currentFrame;
    private JTextField id_Check_TextField;
    private JTextField[] modify_TextField = new JTextField[6];
    private JTextField[] insert_TextField = new JTextField[6];
    public String tag;
    public frame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        memberManagementButton = new JButton("회원관리");
        memberManagementButton.addActionListener(this);
        memberManagementButton.setPreferredSize(new Dimension(100, 50)); // 버튼 크기 설정

        bookManagementButton = new JButton("도서관리");
        bookManagementButton.addActionListener(this);
        bookManagementButton.setPreferredSize(new Dimension(100,50));

        borrowManagementButton = new JButton("대출이력");
        borrowManagementButton.addActionListener(this);
        borrowManagementButton.setPreferredSize(new Dimension(100,50));

        setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        add(memberManagementButton);
        add(bookManagementButton);
        add(borrowManagementButton);

        setSize(1200, 600);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == memberManagementButton) {
            tag = "고객";
            showMemberManagementFrame();
        } else if (e.getSource() == bookManagementButton) {
            tag = "도서";
            showBookManagementFrame();
        }else if (e.getSource() == borrowManagementButton) {
            tag = "대출이력";
            showBorrowManagementFrame();
        } else if (e.getSource() == modifyButton) {
            showmodifyFrame();
        } else if (e.getSource() == insertButton) {
            showInsertFrame();
        }else if (e.getSource() == lateButton){
            showLateFrame();
        }  else if (e.getSource() == c_selectButton) {
            showMemberDate(tag,1);
        }  else if (e.getSource() == b_selectButton) {
            showMemberDate(tag,1);
        }  else if (e.getSource() == r_selectButton) {
            showMemberDate(tag,1);
        }else if (e.getSource()==check_ID_btn){
            String txt = id_Check_TextField.getText();
            int id = Integer.parseInt(txt);
            if (tag=="고객") {
                if (sql.check_ID(id, tag, "고객_ID")) {
                    showLog("고객 정보 존재");
                } else
                    showLog("존재하지 않는 ID 입니다.");
            }else if(tag=="도서") {
                if (sql.check_ID(id, tag, "도서_ID")) {
                    showLog("도서 정보 존재");
                } else
                    showLog("존재하지 않는 ID 입니다.");
            }
            else if(tag=="대출이력") {
                if (sql.check_ID(id, tag, "대출_ID")) {
                    showLog("대출 정보 존재");
                } else
                    showLog("존재하지 않는 ID 입니다.");
            }
        }else if (e.getSource()==modify_btn){
            if(tag == "고객") {
                String txt = id_Check_TextField.getText();
                int id = Integer.parseInt(txt);
                String name = modify_TextField[0].getText();
                String phone = modify_TextField[1].getText();
                String email = modify_TextField[2].getText();
                String address = modify_TextField[3].getText();
                sql.c_UpdateData(tag, id, name, phone, email, address);
                showLog("고객 정보가 수정되었습니다.");
            }else if(tag=="도서"){
                String txt = id_Check_TextField.getText();
                int id = Integer.parseInt(txt);
                String name = modify_TextField[0].getText();
                String author = modify_TextField[1].getText();
                String publisher = modify_TextField[2].getText();
                String year = modify_TextField[3].getText();
                String category = modify_TextField[4].getText();
                String count = modify_TextField[5].getText();
                int num = Integer.parseInt(count);
                sql.b_UpdateData(tag, id, name, author,publisher, year, category, num);
                showLog("도서 정보가 수정되었습니다.");
            }
        }else if (e.getSource()==insert_btn) {
            if (tag == "고객") {
                String txt = id_Check_TextField.getText();
                int id = Integer.parseInt(txt);
                String name = insert_TextField[0].getText();
                String phone = insert_TextField[1].getText();
                String email = insert_TextField[2].getText();
                String address = insert_TextField[3].getText();
                sql.c_InsertData(tag, id, name, phone, email, address);
                showLog("신규 고객 정보가 입력되었습니다.");
            } else if (tag == "도서") {
                String txt = id_Check_TextField.getText();
                int id = Integer.parseInt(txt);
                String name = insert_TextField[0].getText();
                String author = insert_TextField[1].getText();
                String publisher = insert_TextField[2].getText();
                String year = insert_TextField[3].getText();
                String category = insert_TextField[4].getText();
                String count = insert_TextField[5].getText();
                int num = Integer.parseInt(count);
                sql.b_InsertData(tag, id, name, author, publisher, year, category, num);
                showLog("신규 도서 정보가 입력되었습니다.");
            } else if (tag == "대출이력") {
                String txt = id_Check_TextField.getText();
                int id = Integer.parseInt(txt);
                String a = insert_TextField[0].getText();
                String b = insert_TextField[1].getText();
                int cus_id = Integer.parseInt(b);
                sql.r_InsertData(tag, id, cus_id, a);
                showLog("신규 대출 정보가 입력되었습니다.");
            }
        }else if (e.getSource()==delete_btn) {
            if (tag == "대출이력") {
                String txt = delete_TextField.getText();
                int id = Integer.parseInt(txt);
                sql.r_deleteData(id);
                showLog("반납 처리 되었습니다.");
            }
            else if(tag == "도서") {
                String txt = delete_TextField.getText();
                int id = Integer.parseInt(txt);
                if(sql.deleteData(tag,"도서_ID",id))
                    showLog("해당 책은 아직 반납하지 않은 고객이 있습니다. 반납을 진행한 후 다시 진행해주세요");
                else
                    showLog("삭제가 완료되었습니다.");
            }
            else if(tag == "고객") {
                String txt = delete_TextField.getText();
                int id = Integer.parseInt(txt);
                if(sql.deleteData(tag,"고객_ID",id))
                    showLog("해당 고객은 아직 반납하지 않은 책이 있습니다. 반납을 진행한 후 다시 진행해주세요");
                else
                    showLog("삭제가 완료되었습니다.");
            }
        }else if (e.getSource()==search_btn) {
            showMemberDate(tag,2);
            showLog("검색이 완료되었습니다.");
        }else if (e.getSource()==c_search_btn) {
            showMemberDate(tag,3);
            showLog("검색이 완료되었습니다.");
        }else if (e.getSource()==b_search_btn){
            showMemberDate(tag, 4);
            showLog("검색이 완료되었습니다.");
        }
        else if (e.getSource()==r_deleteButton){
            String txt = r_delete_TextField.getText();
            int id = Integer.parseInt(txt);
            sql.delete(id);
            showLog("대출기록 삭제가 완료되었습니다.");
        }
    }
    private void showLateFrame(){
        currentFrame = new JFrame("조회");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        JScrollPane scrollPane = null;
        scrollPane = new JScrollPane(sql.nation());

        currentFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showMemberManagementFrame() {
        currentFrame = new JFrame("회원관리");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

        c_selectButton = new JButton("회원정보 조회");
        modifyButton = new JButton("회원정보 수정");
        insertButton = new JButton("회원정보 입력");

        c_selectButton.addActionListener(this);
        c_selectButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        modifyButton.addActionListener(this);
        modifyButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        insertButton.addActionListener(this);
        insertButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정

        currentFrame.add(c_selectButton);
        currentFrame.add(modifyButton);
        currentFrame.add(insertButton);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showBookManagementFrame() {
        currentFrame = new JFrame("도서관리");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

        b_selectButton = new JButton("도서정보 조회");
        modifyButton = new JButton("도서정보 수정");
        insertButton = new JButton("도서정보 입력");

        b_selectButton.addActionListener(this);
        b_selectButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        modifyButton.addActionListener(this);
        modifyButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        insertButton.addActionListener(this);
        insertButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정

        currentFrame.add(b_selectButton);
        currentFrame.add(modifyButton);
        currentFrame.add(insertButton);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showBorrowManagementFrame() {
        currentFrame = new JFrame("대출이력");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

        r_selectButton = new JButton("대출정보 조회");
        insertButton = new JButton("대출정보 입력");
        lateButton = new JButton("연체자 명단 조회");

        r_selectButton.addActionListener(this);
        r_selectButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        insertButton.addActionListener(this);
        insertButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정
        lateButton.addActionListener(this);
        lateButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 설정

        currentFrame.add(r_selectButton);
        currentFrame.add(insertButton);
        currentFrame.add(lateButton);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showMemberDate(String a,int b) {
        currentFrame = new JFrame("조회");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        JScrollPane scrollPane = null;
        if(b==1){
            scrollPane = new JScrollPane(sql.select(a));
        }else if(b==2){
            if(a=="대출이력"){
                String txt = search_TextField.getText();
                int id = Integer.parseInt(txt);
                scrollPane = new JScrollPane(sql.id_select(tag,"대출_ID",id));
            }else if(a=="고객") {
                String txt = search_TextField.getText();
                int id = Integer.parseInt(txt);
                scrollPane = new JScrollPane(sql.id_select(tag,"고객_ID",id));
            }else if(a=="도서"){
                String txt = search_TextField.getText();
                int id = Integer.parseInt(txt);
                scrollPane = new JScrollPane(sql.id_select(tag,"도서_ID",id));
            }
        }
        else if(b==3) {
            if (a == "고객") {
                String txt = c_search_TextField.getText();
                scrollPane = new JScrollPane(sql.name_select(tag, "이름", txt));
            } else if (a == "도서") {
                String txt = c_search_TextField.getText();
                scrollPane = new JScrollPane(sql.name_select(tag, "도서명", txt));
            } else if (a=="대출이력"){
                String txt = c_search_TextField.getText();
                int id = Integer.parseInt(txt);
                scrollPane = new JScrollPane(sql.id_select(tag,"고객_ID",id));
            }
        }else if(b==4){
            if(a=="대출이력"){
                String txt = b_search_TextField.getText();
                int id = Integer.parseInt(txt);
                scrollPane = new JScrollPane(sql.id_select(tag,"도서_ID",id));
            }
        }
        String deleteButtonText = null;
        if(tag=="고객"|tag=="도서"){
            deleteButtonText = "삭제하기";
        }else if(tag=="대출이력"){
            deleteButtonText = "반납하기";
        }
        delete_btn = new JButton(deleteButtonText);
        delete_btn.addActionListener(this);
        delete_TextField = new JTextField(20);
        if(tag=="고객"|tag=="도서") {
            search_TextField = new JTextField(20);
            search_btn = new JButton("ID로 검색하기");
            search_btn.addActionListener(this);
            c_search_TextField = new JTextField(20);
            c_search_btn = new JButton("이름으로 검색하기");
            c_search_btn.addActionListener(this);
        }else if(tag=="대출이력"){
            search_btn = new JButton("대출번호로 검색하기");
            search_TextField = new JTextField(20);
            c_search_btn = new JButton("고객번호로 검색하기");
            c_search_btn.addActionListener(this);
            c_search_TextField = new JTextField(20);
            b_search_btn = new JButton("도서번호로 검색하기");
            b_search_btn.addActionListener(this);
            b_search_TextField = new JTextField(20);
            r_deleteButton = new JButton("삭제하기");
            r_deleteButton.addActionListener(this);
            r_delete_TextField = new JTextField(20);
        }
        currentFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        bottomPanel.add(c_search_TextField);
        bottomPanel.add(c_search_btn);
        if(tag=="대출이력"){
            bottomPanel.add(b_search_TextField);
            bottomPanel.add(b_search_btn);
        }
        bottomPanel.add(search_TextField);
        bottomPanel.add(search_btn);
        bottomPanel.add(delete_TextField);
        bottomPanel.add(delete_btn);
        if(tag=="대출이력"){
            bottomPanel.add(r_delete_TextField);
            bottomPanel.add(r_deleteButton);
        }
        currentFrame.add(bottomPanel, BorderLayout.CENTER);


        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showmodifyFrame() {
        currentFrame = new JFrame("수정");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        JScrollPane scrollPane = null;
        scrollPane = new JScrollPane(sql.select(tag));
        JPanel panel = new JPanel();
        JLabel[] modify_Label = new JLabel[7];
        if(tag == "고객") {
            modify_Label = new JLabel[5];
            modify_Label[0] = new JLabel("회원_ID");
            id_Check_TextField = new JTextField(20);
            check_ID_btn = new JButton("ID_확인");
            check_ID_btn.addActionListener(this);
            modify_btn = new JButton("수정하기");
            modify_btn.addActionListener(this);
            modify_Label[1] = new JLabel("   이름    ");
            modify_TextField[0] = new JTextField(20);
            modify_Label[2] = new JLabel("전화번호");
            modify_TextField[1] = new JTextField(20);
            modify_Label[3] = new JLabel("메일주소");
            modify_TextField[2] = new JTextField(20);
            modify_Label[4] = new JLabel("   주소    ");
            modify_TextField[3] = new JTextField(20);
        }
        else if (tag=="도서"){
            modify_Label = new JLabel[7];
            modify_Label[0] = new JLabel("도서_ID");
            id_Check_TextField = new JTextField(20);
            check_ID_btn = new JButton("ID_확인");
            check_ID_btn.addActionListener(this);
            modify_btn = new JButton("수정하기");
            modify_btn.addActionListener(this);

            modify_Label[1] = new JLabel("   제목    ");
            modify_TextField[0] = new JTextField(20);
            modify_Label[2] = new JLabel("   저자    ");
            modify_TextField[1] = new JTextField(20);
            modify_Label[3] = new JLabel("  출판사   ");
            modify_TextField[2] = new JTextField(20);
            modify_Label[4] = new JLabel("출판년도");
            modify_TextField[3] = new JTextField(20);
            modify_Label[5] = new JLabel("   장르    ");
            modify_TextField[4] = new JTextField(20);
            modify_Label[6] = new JLabel("대출가능권수");
            modify_TextField[5] = new JTextField(20);
        }
        currentFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(8,1));
        JPanel[] row = new JPanel[7];
        for (int i = 0; i < row.length; i++) {
            row[i] = new JPanel();
        }
        row[0].add(modify_Label[0]);
        row[0].add(id_Check_TextField);
        row[0].add(check_ID_btn);
        row[1].add(modify_Label[1]);
        row[1].add(modify_TextField[0]);
        row[2].add(modify_Label[2]);
        row[2].add(modify_TextField[1]);
        row[3].add(modify_Label[3]);
        row[3].add(modify_TextField[2]);
        row[4].add(modify_Label[4]);
        row[4].add(modify_TextField[3]);
        if(tag=="도서") {
            row[5].add(modify_Label[5]);
            row[5].add(modify_TextField[4]);
            row[6].add(modify_Label[6]);
            row[6].add(modify_TextField[5]);
        }

        panel.add(row[0]);
        panel.add(row[1]);
        panel.add(row[2]);
        panel.add(row[3]);
        panel.add(row[4]);
        if(tag=="도서"){
            panel.add(row[5]);
            panel.add(row[6]);
        }
        panel.add(modify_btn);
        currentFrame.getContentPane().add(panel, BorderLayout.CENTER);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }
    private void showInsertFrame() {
        currentFrame = new JFrame("입력");
        currentFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        JScrollPane scrollPane = null;
        scrollPane = new JScrollPane(sql.select(tag));
        JPanel panel = new JPanel();
        JLabel[] insert_Label = new JLabel[7];
        if(tag == "고객") {
            insert_Label = new JLabel[5];
            insert_Label[0] = new JLabel("회원_ID");
            id_Check_TextField = new JTextField(20);
            check_ID_btn = new JButton("ID_확인");
            check_ID_btn.addActionListener(this);
            insert_btn = new JButton("입력하기");
            insert_btn.addActionListener(this);
            insert_Label[1] = new JLabel("   이름    ");
            insert_TextField[0] = new JTextField(20);
            insert_Label[2] = new JLabel("전화번호");
            insert_TextField[1] = new JTextField(20);
            insert_Label[3] = new JLabel("메일주소");
            insert_TextField[2] = new JTextField(20);
            insert_Label[4] = new JLabel("   주소    ");
            insert_TextField[3] = new JTextField(20);
        }
        else if (tag=="도서"){
            insert_Label = new JLabel[7];
            insert_Label[0] = new JLabel("도서_ID");
            id_Check_TextField = new JTextField(20);
            check_ID_btn = new JButton("ID_확인");
            check_ID_btn.addActionListener(this);
            insert_btn = new JButton("입력하기");
            insert_btn.addActionListener(this);

            insert_Label[1] = new JLabel("   제목    ");
            insert_TextField[0] = new JTextField(20);
            insert_Label[2] = new JLabel("   저자    ");
            insert_TextField[1] = new JTextField(20);
            insert_Label[3] = new JLabel("  출판사   ");
            insert_TextField[2] = new JTextField(20);
            insert_Label[4] = new JLabel("출판년도");
            insert_TextField[3] = new JTextField(20);
            insert_Label[5] = new JLabel("   장르    ");
            insert_TextField[4] = new JTextField(20);
            insert_Label[6] = new JLabel("대출가능권수");
            insert_TextField[5] = new JTextField(20);
        }else if (tag=="대출이력"){
            insert_Label = new JLabel[3];
            insert_Label[0] = new JLabel("대출_ID");
            id_Check_TextField = new JTextField(20);
            check_ID_btn = new JButton("ID_확인");
            check_ID_btn.addActionListener(this);
            insert_btn = new JButton("대출하기");
            insert_btn.addActionListener(this);

            insert_Label[1] = new JLabel(" 도서명 ");
            insert_TextField[0] = new JTextField(20);
            insert_Label[2] = new JLabel("회원_ID");
            insert_TextField[1] = new JTextField(20);

        }
        currentFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(8,1));
        JPanel[] row = new JPanel[7];
        for (int i = 0; i < row.length; i++) {
            row[i] = new JPanel();
        }
        row[0].add(insert_Label[0]);
        row[0].add(id_Check_TextField);
        row[0].add(check_ID_btn);
        row[1].add(insert_Label[1]);
        row[1].add(insert_TextField[0]);
        row[2].add(insert_Label[2]);
        row[2].add(insert_TextField[1]);
        if(tag=="도서"|tag=="고객") {
            row[3].add(insert_Label[3]);
            row[3].add(insert_TextField[2]);
            row[4].add(insert_Label[4]);
            row[4].add(insert_TextField[3]);
        }
        if(tag=="도서") {
            row[5].add(insert_Label[5]);
            row[5].add(insert_TextField[4]);
            row[6].add(insert_Label[6]);
            row[6].add(insert_TextField[5]);
        }

        panel.add(row[0]);
        panel.add(row[1]);
        panel.add(row[2]);
        if(tag=="도서"|tag=="고객") {
            panel.add(row[3]);
            panel.add(row[4]);
        }
        if(tag=="도서"){
            panel.add(row[5]);
            panel.add(row[6]);
        }
        panel.add(insert_btn);
        currentFrame.getContentPane().add(panel, BorderLayout.CENTER);

        currentFrame.setSize(1200, 600);
        currentFrame.setVisible(true);
    }

    public void showLog(String a){
        JOptionPane.showMessageDialog(currentFrame,a,"알림",JOptionPane.INFORMATION_MESSAGE);
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new frame();
            }
        });
    }
}