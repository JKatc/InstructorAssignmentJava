//Filename InstructorAssignment
//Written by Jonathan Kinney
//Written on 5/21/2016
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
public class InstructorAssignment extends JFrame implements ActionListener, ItemListener {
    //declare variables
    private ArrayList<String> instructors = new ArrayList<>();
    private ArrayList<String> instructorList = new ArrayList<>();
    private ArrayList<String> availableInsts = new ArrayList<>();
    private ArrayList<String> students = new ArrayList<>();
    private ArrayList<String> studentList = new ArrayList<>();
    private ArrayList<String> availableStuds = new ArrayList<>();
    private ArrayList<String> firstInst = new ArrayList<>();
    private ArrayList<String> b1Inst = new ArrayList<>();
    private ArrayList<String> b2Inst = new ArrayList<>();
    private ArrayList<String> interInst = new ArrayList<>();
    private ArrayList<String> firstStud = new ArrayList<>();
    private ArrayList<String> b1Stud = new ArrayList<>();
    private ArrayList<String> b2Stud = new ArrayList<>();
    private ArrayList<String> interStud = new ArrayList<>();
    private ArrayList<String> scheduleStrings = new ArrayList<>();
    private int studPerInst = 0;
    private int numOfFiles;
    private JButton addInstButton = new JButton("Manage Instructors");
    private JButton addStudButton = new JButton("Manage Students");
    private JButton delLogsButton = new JButton("Delete Logs");
    private JButton createSchedButton = new JButton("Create schedule");
    private JLabel errorLabel = new JLabel();
    private JFrame instFrame1 = new JFrame("Instructors");
    private JPanel instPanel1 = new JPanel();
    private JPanel instPanel2 = new JPanel();
    private JPanel instPanel3 = new JPanel();
    private JPanel instPanel4 = new JPanel();
    private JPanel instPanel5 = new JPanel();
    private JLabel instLabelName = new JLabel(" Instructor's full name: ");
    private JLabel instLabelLevel = new JLabel(" Instructor's teaching level: ");
    private JLabel instLabelCurInsts = new JLabel(" Current instructors: ");
    private JTextField instField1 = new JTextField(28);
    private JComboBox<String> instSelLevel = new JComboBox<>();
    private JTextArea instTA1 = new JTextArea();
    private JButton instButtonReturn = new JButton("Return");
    private JButton instButtonAdd = new JButton("Add");
    private JButton instButtonDelete = new JButton("Delete");
    private JScrollPane instScroll = new JScrollPane(instTA1);
    private JFrame studFrame1 = new JFrame("Students");
    private JPanel studPanel1 = new JPanel();
    private JPanel studPanel2 = new JPanel();
    private JPanel studPanel3 = new JPanel();
    private JPanel studPanel4 = new JPanel();
    private JPanel studPanel5 = new JPanel();
    private JLabel studLabelName = new JLabel(" Student's full name: ");
    private JLabel studLabelLevel = new JLabel(" Student's level: ");
    private JLabel studLabelCurStuds = new JLabel(" Current students: ");
    private JTextField studField1 = new JTextField(28);
    private JComboBox<String> studSelLevel = new JComboBox<>();
    private JTextArea studTA1 = new JTextArea();
    private JButton studButtonReturn = new JButton("Return");
    private JButton studButtonAdd = new JButton("Add");
    private JButton studButtonDelete = new JButton("Delete");
    private JScrollPane studScroll = new JScrollPane(studTA1);
    private JFrame schedFrame1 = new JFrame("Schedule");
    private JPanel schedPanel1 = new JPanel();
    private JPanel schedPanel2 = new JPanel();
    private JPanel schedPanel3 = new JPanel();
    private JPanel schedPanel4 = new JPanel();
    private JPanel schedPanel5 = new JPanel();
    private JPanel schedPanel6 = new JPanel();
    private JLabel schedLabelStudPerInst = new JLabel(" Students per instructor: ");
    private JLabel schedLabelCurrentInsts = new JLabel(" Instructors: ");
    private JLabel schedLabelCurrentStuds = new JLabel(" Students: ");
    private JTextField schedField1 = new JTextField("8", 12);
    private JButton schedButtonReturn = new JButton("Return");
    private JButton schedButtonCreate = new JButton("Create");
    private JTextArea schedTA1 = new JTextArea();
    private JScrollPane schedScroll = new JScrollPane(schedTA1);
    private ArrayList<JCheckBox> schedCheckBoxesInst = new ArrayList<>();
    private ArrayList<JCheckBox> schedCheckBoxesStud = new ArrayList<>();
    private JFrame clearFrame1 = new JFrame("Clear logs");
    private JPanel clearPanel1 = new JPanel();
    private JLabel clearLabel1 = new JLabel();
    private JButton clearButtonDelInsts = new JButton("Delete all instructors");
    private JButton clearButtonDelStuds = new JButton("Delete all students");
    private JButton clearButtonDelHist = new JButton();
    private JButton clearButtonReturn = new JButton("Return");
    private Path instFile = Paths.get("instructorlist.txt");
    private Path studFile = Paths.get("studentlist.txt");
    OutputStream output = null;
    InputStream input = null;

    //constructor
    public InstructorAssignment() {
        super("Instructor Assignment");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(addInstButton);
        add(addStudButton);
        add(delLogsButton);
        add(createSchedButton);
        instSelLevel.addItem("First");
        instSelLevel.addItem("Beginner 1");
        instSelLevel.addItem("Beginner 2");
        instSelLevel.addItem("Intermediate");
        studSelLevel.addItem("First");
        studSelLevel.addItem("Beginner 1");
        studSelLevel.addItem("Beginner 2");
        studSelLevel.addItem("Intermediate");
        addInstButton.addActionListener(this);
        addStudButton.addActionListener(this);
        delLogsButton.addActionListener(this);
        createSchedButton.addActionListener(this);

        //create files
        try {
            output = new BufferedOutputStream(Files.newOutputStream(instFile, CREATE));
            output.close();
            output = new BufferedOutputStream(Files.newOutputStream(studFile, CREATE));
            output.close();
        } catch (IOException err) {
            add(errorLabel);
            errorLabel.setText(err.getMessage());
        }
    } //end of constructor

    //buttons
    public void actionPerformed(ActionEvent e) {
        //declare variables
        Object source = e.getSource();
        String writeString;
        if (source == addInstButton) { //go to add/manage instructors frame
            setVisible(false);
            addInstructors();
        } else if (source == addStudButton) { //go to add/manage students frame
            setVisible(false);
            addStudents();
        } else if (source == delLogsButton) { //go to delete schedule logs, students, or instructors frame
            clearLabel1.setText("");
            setVisible(false);
            clearFrame();
        } else if (source == createSchedButton) { //go to manage and create schedule frame
            availableInsts.clear();
            availableStuds.clear();
            instructorList.clear();
            try {
                input = Files.newInputStream(instFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String readerString;
                while ((readerString = reader.readLine()) != null) {
                    instructorList.add(readerString);
                }
                input.close();
            } catch (IOException err) {
                instTA1.setText("File could not be accessed.");
            }
            sortInstLevels();

            studentList.clear();
            try {
                input = Files.newInputStream(studFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String readerString;
                while ((readerString = reader.readLine()) != null) {
                    studentList.add(readerString);
                }
                input.close();
            } catch (IOException err) {
                studTA1.setText("File could not be accessed.");
            }

            sortStudLevels();

            schedCheckBoxesInst.clear();
            schedCheckBoxesStud.clear();

            for (int x = 0; x < instructorList.size(); ++x) {
                JCheckBox[] schedBoxes = new JCheckBox[instructorList.size()];
                for (int y = 0; y < schedBoxes.length; ++y)
                    schedBoxes[y] = new JCheckBox();
                schedBoxes[x].setText(instructorList.get(x));
                schedCheckBoxesInst.add(schedBoxes[x]);
                schedCheckBoxesInst.get(x).setToolTipText(schedCheckBoxesInst.get(x).getText());
            }
            for (int x = 0; x < studentList.size(); ++x) {
                JCheckBox[] schedBoxes = new JCheckBox[studentList.size()];
                for (int y = 0; y < schedBoxes.length; ++y)
                    schedBoxes[y] = new JCheckBox();
                schedBoxes[x].setText(studentList.get(x));
                schedCheckBoxesStud.add(schedBoxes[x]);
                schedCheckBoxesStud.get(x).setToolTipText(schedCheckBoxesStud.get(x).getText());
            }

            setVisible(false);
            scheduleFrame();
        } else if (source == instButtonReturn) { //return to main frame from instructor frame button
            instButtonReturn.removeActionListener(this);
            instButtonAdd.removeActionListener(this);
            instButtonDelete.removeActionListener(this);

            instFrame1.setVisible(false);
            instFrame1.dispose();
            instField1.setText("");
            instTA1.setText("");
            setVisible(true);
        } else if (source == instButtonAdd) { //add instructor button
            String nameAndLevel;
            instButtonDelete.setText("Delete");
            if (!instField1.getText().equals("")) {
                try {
                    output = new BufferedOutputStream(Files.newOutputStream(instFile, CREATE, APPEND));
                    nameAndLevel = instField1.getText() + " | " + instSelLevel.getSelectedItem();
                    instructors.add(nameAndLevel);
                    writeString = nameAndLevel + System.lineSeparator();
                    byte[] data = writeString.getBytes();
                    output.write(data);
                    output.flush();
                    output.close();

                    instructorList.add(nameAndLevel);
                    sortInstLevels();
                    instTA1.setText("");

                    for (int x = 0; x < instructorList.size(); ++x)
                        instTA1.append(instructorList.get(x) + System.lineSeparator());

                } catch (IOException err) {
                    instButtonAdd.setText("File could not be accessed");
                }
                instField1.setText("");
            }
        } else if (source == instButtonDelete) { //delete instructor button
            instButtonDelete.setText("Delete");
            boolean found = false;
            String line;
            String deleteString = instField1.getText() + " | " + instSelLevel.getSelectedItem();

            instField1.setText("");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(instFile.toString()));
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals(deleteString)) {
                        found = true;
                    }
                }
                reader.close();
            } catch (IOException err) {
                instButtonDelete.setText("Delete (File not found.)");
            }

            if (found) {
                try {
                    File instInputFile = new File(instFile.toString());
                    BufferedReader reader = new BufferedReader(new FileReader(instFile.toString()));
                    File tempFile = new File(instInputFile.getAbsolutePath() + ".txt");

                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().equals(deleteString)) {
                            instructorList.remove(line);
                            writer.write(line + System.lineSeparator());
                            writer.flush();
                        }
                    }
                    writer.close();
                    reader.close();

                    if (!instInputFile.delete()) {
                        instButtonDelete.setText("Delete (File could not be deleted)");
                    }

                    if (!tempFile.renameTo(instInputFile))
                        instButtonDelete.setText("Delete (File could not be renamed)");
                } catch (IOException err) {
                    instButtonDelete.setText("Delete (File not found or could not be deleted)");
                }

                instructorList.clear();
                try {
                    input = Files.newInputStream(instFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String readerString;
                    int x = 0;
                    while ((readerString = reader.readLine()) != null) {
                        instructorList.add(readerString);
                        ++x;
                    }
                    sortInstLevels();
                    instTA1.setText("");
                    for (x = 0; x < instructorList.size(); ++x)
                        instTA1.append(instructorList.get(x) + System.lineSeparator());
                    input.close();
                } catch (IOException err) {
                    instTA1.setText("File could not be accessed.");
                }
            } else if (!found && !instField1.getText().equals(""))
                instButtonDelete.setText("Delete (Instructor not found.)");
        } else if (source == studButtonReturn) { //return to main frame from student frame button
            studButtonReturn.removeActionListener(this);
            studButtonAdd.removeActionListener(this);
            studButtonDelete.removeActionListener(this);

            studFrame1.setVisible(false);
            studFrame1.dispose();
            studField1.setText("");
            studTA1.setText("");
            setVisible(true);
        } else if (source == studButtonAdd) { //add student button
            String nameAndLevel;
            studButtonDelete.setText("Delete");
            if (!studField1.getText().equals("")) {
                try {
                    output = new BufferedOutputStream(Files.newOutputStream(studFile, CREATE, APPEND));
                    nameAndLevel = studField1.getText() + " | " + studSelLevel.getSelectedItem();
                    students.add(nameAndLevel);
                    writeString = nameAndLevel + System.lineSeparator();
                    byte[] data = writeString.getBytes();
                    output.write(data);
                    output.flush();
                    output.close();

                    studentList.add(nameAndLevel);
                    sortStudLevels();
                    studTA1.setText("");

                    for (int x = 0; x < studentList.size(); ++x)
                        studTA1.append(studentList.get(x) + System.lineSeparator());

                } catch (IOException err) {
                    studButtonAdd.setText("File could not be accessed");
                }
                studField1.setText("");
            }
        } else if (source == studButtonDelete) { //delete student button
            studButtonDelete.setText("Delete");
            boolean found = false;
            String line;
            String deleteString = studField1.getText() + " | " + studSelLevel.getSelectedItem();

            studField1.setText("");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(studFile.toString()));
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals(deleteString)) {
                        found = true;
                    }
                }
                reader.close();
            } catch (IOException err) {
                studButtonDelete.setText("Delete (File not found.)");
            }

            if (found) {
                try {
                    File studInputFile = new File(studFile.toString());
                    BufferedReader reader = new BufferedReader(new FileReader(studFile.toString()));
                    File tempFile = new File(studInputFile.getAbsolutePath() + ".txt");

                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().equals(deleteString)) {
                            studentList.remove(line);
                            writer.write(line + System.lineSeparator());
                            writer.flush();
                        }
                    }
                    writer.close();
                    reader.close();

                    if (!studInputFile.delete()) {
                        studButtonDelete.setText("Delete (File could not be deleted)");
                    }

                    if (!tempFile.renameTo(studInputFile))
                        studButtonDelete.setText("Delete (File could not be renamed)");
                } catch (IOException err) {
                    studButtonDelete.setText("Delete (File not found or could not be deleted)");
                }

                studentList.clear();
                try {
                    input = Files.newInputStream(studFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String readerString;
                    int x = 0;
                    while ((readerString = reader.readLine()) != null) {
                        studentList.add(readerString);
                        ++x;
                    }
                    sortStudLevels();
                    studTA1.setText("");
                    for (x = 0; x < studentList.size(); ++x)
                        studTA1.append(studentList.get(x) + System.lineSeparator());
                    input.close();
                } catch (IOException err) {
                    studTA1.setText("File could not be accessed.");
                }
            } else if (!found && !studField1.getText().equals(""))
                studButtonDelete.setText("Delete (Student not found.)");
        } else if (source == schedButtonReturn) { //return to main frame from schedule frame button
            schedButtonReturn.removeActionListener(this);
            schedButtonCreate.removeActionListener(this);

            schedFrame1.setVisible(false);
            schedFrame1.dispose();
            setVisible(true);
        } else if (source == schedButtonCreate) { //create schedule button
            String studPerInstString = schedField1.getText();
            schedButtonCreate.setText("Create");
            try {
                studPerInst = Integer.parseInt(studPerInstString);
            }
            catch(NumberFormatException err)
            {
                schedButtonCreate.setText("Create (Non-numeric data inputted. Default value will be used.)");
                studPerInst = 8;
            }
            schedTA1.setText("");

            calculateSchedule();
            int dialogButton = JOptionPane.showConfirmDialog(null, "Would you like to export this schedule to a text file?", "", JOptionPane.YES_NO_OPTION);
            if(dialogButton == JOptionPane.YES_OPTION)
            {
                try {
                    int x = 1;
                    File f = new File("scheduleFile" + x + ".txt");
                    while(f.exists()) {
                        ++x;
                        f = new File("scheduleFile" + x + ".txt");
                    }
                    numOfFiles = x;
                    FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
                    schedTA1.write(fw);
                    schedTA1.append("Exported to scheduleFile" + (numOfFiles) + ".txt");
                    fw.close();
                }
                catch(IOException err)
                {
                    schedTA1.append("File could not be accessed.");
                }
            }
        } else if (source == clearButtonDelInsts) { //delete all instructors button
            int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all instructors?", "WARNING", JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    Files.delete(instFile);
                    clearLabel1.setText("Instructors cleared.");
                } catch (IOException err) {
                    clearLabel1.setText("File could not be accessed, or no instructors exist.");
                }

                try {
                    output = new BufferedOutputStream(Files.newOutputStream(instFile, CREATE));
                    output.close();
                } catch (IOException err) {
                    clearLabel1.setText(err.getMessage());
                }
            }
        } else if (source == clearButtonDelStuds) { //delete all students button
            int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all students?", "WARNING", JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    Files.delete(studFile);
                    clearLabel1.setText("Students cleared.");
                } catch (IOException err) {
                    clearLabel1.setText("File could not be accessed, or no students exist.");
                }

                try {
                    output = new BufferedOutputStream(Files.newOutputStream(studFile, CREATE));
                    output.close();
                } catch (IOException err) {
                    clearLabel1.setText(err.getMessage());
                }
            }
        } else if (source == clearButtonDelHist) { //delete all schedule logs button
            int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all schedules?", "WARNING", JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                if(numOfFiles-1 != 0) {
                    try {
                        for (int x = 1; x < numOfFiles; ++x) {
                            Path scheduleFilePath = Paths.get("scheduleFile" + x + ".txt");
                            Files.delete(scheduleFilePath);
                        }

                        clearLabel1.setText("Schedule history cleared.");
                        clearButtonDelHist.setText("Delete all schedule history (" + 0 + " files)");
                    } catch (IOException err) {
                        clearLabel1.setText("Files could not be accessed, or no schedules exist.");
                    }
                }
                else
                    clearLabel1.setText("No schedules exist.");
            }
            numOfFiles = 1;
        } else if (source == clearButtonReturn) { //return to main frame from clear frame button
            clearButtonDelInsts.removeActionListener(this);
            clearButtonDelStuds.removeActionListener(this);
            clearButtonDelHist.removeActionListener(this);
            clearButtonReturn.removeActionListener(this);

            clearFrame1.setVisible(false);
            clearFrame1.dispose();
            setVisible(true);
        }
    } //end of actionPerformed

    public void itemStateChanged(ItemEvent ie) {
        //declare variables
        JCheckBox source = (JCheckBox) ie.getSource();
        boolean instructorBox = false;
        boolean studentBox = false;

        for (int x = 0; x < schedCheckBoxesInst.size(); ++x) {
            if (schedCheckBoxesInst.get(x) == source)
                instructorBox = true;
        }

        for (int x = 0; x < schedCheckBoxesStud.size(); ++x) {
            if (schedCheckBoxesStud.get(x) == source)
                studentBox = true;
        }

        if (ie.getStateChange() == ItemEvent.SELECTED && instructorBox) {
            availableInsts.add(source.getText());
        } else if (ie.getStateChange() == ItemEvent.DESELECTED && instructorBox) {
            availableInsts.remove(source.getText());
        } else if (ie.getStateChange() == ItemEvent.SELECTED && studentBox) {
            availableStuds.add(source.getText());
        } else if (ie.getStateChange() == ItemEvent.DESELECTED && studentBox) {
            availableStuds.remove(source.getText());
        }
    } //end of itemStateChanged

    public void addInstructors() {
        //create frame
        instFrame1.setSize(500, 500);
        instFrame1.setVisible(true);
        instFrame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        instFrame1.setLayout(new BorderLayout());
        instPanel1.setLayout(new BorderLayout());
        instPanel2.setLayout(new BorderLayout());
        instPanel3.setLayout(new BorderLayout());
        instPanel4.setLayout(new BorderLayout());
        instPanel5.setLayout(new BorderLayout());

        instPanel4.add(instPanel1, BorderLayout.NORTH);
        instPanel4.add(instPanel3, BorderLayout.SOUTH);
        instPanel1.add(instLabelName, BorderLayout.WEST);
        instPanel1.add(instField1, BorderLayout.EAST);
        instPanel2.add(instButtonReturn, BorderLayout.SOUTH);
        instPanel3.add(instLabelLevel, BorderLayout.WEST);
        instPanel3.add(instSelLevel, BorderLayout.EAST);
        instPanel3.add(instButtonAdd, BorderLayout.SOUTH);
        instPanel5.add(instButtonAdd, BorderLayout.NORTH);
        instPanel5.add(instButtonDelete, BorderLayout.CENTER);
        instPanel5.add(instLabelCurInsts, BorderLayout.SOUTH);
        instPanel3.add(instPanel5, BorderLayout.SOUTH);
        instFrame1.add(instPanel4, BorderLayout.NORTH);
        instFrame1.add(instScroll, BorderLayout.CENTER);
        instFrame1.add(instPanel2, BorderLayout.SOUTH);

        instTA1.setLineWrap(true);
        instTA1.setWrapStyleWord(true);
        instTA1.setEditable(false);

        instructorList.clear();
        try {
            input = Files.newInputStream(instFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String readerString;
            while ((readerString = reader.readLine()) != null) {
                instructorList.add(readerString);
            }
            input.close();
        } catch (IOException err) {
            instTA1.setText("File could not be accessed.");
        }

        sortInstLevels();

        for (int x = 0; x < instructorList.size(); ++x) {
            instTA1.append(instructorList.get(x) + System.lineSeparator());
        }

        instButtonReturn.addActionListener(this);
        instButtonAdd.addActionListener(this);
        instButtonDelete.addActionListener(this);
    } //end of addInstructors method

    public void addStudents() {
        //create frame
        studFrame1.setSize(500, 500);
        studFrame1.setVisible(true);
        studFrame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        studFrame1.setLayout(new BorderLayout());
        studPanel1.setLayout(new BorderLayout());
        studPanel2.setLayout(new BorderLayout());
        studPanel3.setLayout(new BorderLayout());
        studPanel4.setLayout(new BorderLayout());
        studPanel5.setLayout(new BorderLayout());

        studPanel4.add(studPanel1, BorderLayout.NORTH);
        studPanel4.add(studPanel3, BorderLayout.SOUTH);
        studPanel1.add(studLabelName, BorderLayout.WEST);
        studPanel1.add(studField1, BorderLayout.EAST);
        studPanel2.add(studButtonReturn, BorderLayout.SOUTH);
        studPanel3.add(studLabelLevel, BorderLayout.WEST);
        studPanel3.add(studSelLevel, BorderLayout.EAST);
        studPanel3.add(studButtonAdd, BorderLayout.SOUTH);
        studPanel5.add(studButtonAdd, BorderLayout.NORTH);
        studPanel5.add(studButtonDelete, BorderLayout.CENTER);
        studPanel5.add(studLabelCurStuds, BorderLayout.SOUTH);
        studPanel3.add(studPanel5, BorderLayout.SOUTH);
        studFrame1.add(studPanel4, BorderLayout.NORTH);
        studFrame1.add(studScroll, BorderLayout.CENTER);
        studFrame1.add(studPanel2, BorderLayout.SOUTH);

        studTA1.setLineWrap(true);
        studTA1.setWrapStyleWord(true);
        studTA1.setEditable(false);

        studentList.clear();
        try {
            input = Files.newInputStream(studFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String readerString;
            while ((readerString = reader.readLine()) != null) {
                studentList.add(readerString);
            }
            input.close();
        } catch (IOException err) {
            studTA1.setText("File could not be accessed.");
        }

        sortStudLevels();

        for (int x = 0; x < studentList.size(); ++x) {
            studTA1.append(studentList.get(x) + System.lineSeparator());
        }

        studButtonReturn.addActionListener(this);
        studButtonAdd.addActionListener(this);
        studButtonDelete.addActionListener(this);
    } //end of addStudents method

    public void scheduleFrame() {
        //create frame
        schedFrame1.setSize(1200, 750);
        schedFrame1.setVisible(true);
        schedFrame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        schedFrame1.setLayout(new BorderLayout());
        schedPanel1.setLayout(new BorderLayout());
        schedPanel2.setLayout(new GridLayout(4, 0));
        schedPanel3.setLayout(new GridLayout(4, 0));
        schedPanel2.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        schedPanel3.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        schedPanel4.setLayout(new BorderLayout());
        schedPanel5.setLayout(new BorderLayout());
        schedPanel6.setLayout(new BorderLayout());

        schedFrame1.add(schedPanel1, BorderLayout.NORTH);
        schedPanel1.add(schedLabelStudPerInst, BorderLayout.WEST);
        schedPanel1.add(schedField1, BorderLayout.CENTER);
        schedPanel1.add(schedPanel4, BorderLayout.SOUTH);
        schedPanel4.add(schedPanel5, BorderLayout.NORTH);
        schedPanel4.add(schedPanel6, BorderLayout.SOUTH);
        schedPanel5.add(schedLabelCurrentInsts, BorderLayout.NORTH);
        schedPanel5.add(schedPanel2, BorderLayout.CENTER);
        schedPanel6.add(schedLabelCurrentStuds, BorderLayout.NORTH);
        schedPanel6.add(schedPanel3, BorderLayout.CENTER);
        schedPanel6.add(schedButtonCreate, BorderLayout.SOUTH);
        schedFrame1.add(schedScroll, BorderLayout.CENTER);
        schedFrame1.add(schedButtonReturn, BorderLayout.SOUTH);

        schedPanel2.removeAll();
        schedPanel3.removeAll();

        for (int x = 0; x < schedCheckBoxesInst.size(); ++x) {
            schedPanel2.add(schedCheckBoxesInst.get(x));
            schedCheckBoxesInst.get(x).addItemListener(this);
        }
        for (int x = 0; x < schedCheckBoxesStud.size(); ++x) {
            schedPanel3.add(schedCheckBoxesStud.get(x));
            schedCheckBoxesStud.get(x).addItemListener(this);
        }

        schedTA1.setLineWrap(true);
        schedTA1.setWrapStyleWord(true);
        schedTA1.setEditable(false);

        schedButtonReturn.addActionListener(this);
        schedButtonCreate.addActionListener(this);
    } //end of scheduleFrame method

    public void clearFrame() {
        //create frame
        clearFrame1.setSize(700, 120);
        clearFrame1.setVisible(true);
        clearFrame1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        clearFrame1.setLayout(new BorderLayout());
        clearPanel1.setLayout(new BorderLayout());

        clearFrame1.add(clearPanel1, BorderLayout.NORTH);
        clearLabel1.setHorizontalAlignment(JLabel.CENTER);
        clearFrame1.add(clearLabel1, BorderLayout.CENTER);
        clearFrame1.add(clearButtonReturn, BorderLayout.SOUTH);
        clearPanel1.add(clearButtonDelInsts, BorderLayout.WEST);
        clearPanel1.add(clearButtonDelStuds, BorderLayout.EAST);
        clearPanel1.add(clearButtonDelHist, BorderLayout.CENTER);

        try {
            int x = 1;
            File f = new File("scheduleFile" + x + ".txt");
            while(f.exists()) {
                ++x;
                f = new File("scheduleFile" + x + ".txt");
            }
            clearButtonDelHist.setText("Delete all schedule history (" + (x-1) + " files)");
            numOfFiles = x;
        }
        catch(Exception err)
        {
            clearLabel1.setText("Schedule files could not be accessed.");
        }

        clearButtonDelInsts.addActionListener(this);
        clearButtonDelStuds.addActionListener(this);
        clearButtonDelHist.addActionListener(this);
        clearButtonReturn.addActionListener(this);
    } //end of clearFrame method

    public void calculateSchedule() {
        //declare and clear variables
        firstInst.clear();
        b1Inst.clear();
        b2Inst.clear();
        interInst.clear();
        firstStud.clear();
        b1Stud.clear();
        b2Stud.clear();
        interStud.clear();
        scheduleStrings.clear();
        int x;
        int y = 0;
        int z;
        int levelStart;
        ArrayList<String> cutFirstInsts = new ArrayList<>();
        ArrayList<String> cutB1Insts = new ArrayList<>();
        ArrayList<String> cutB2Insts = new ArrayList<>();
        ArrayList<String> cutInterInsts = new ArrayList<>();
        ArrayList<String> unremFirstStuds = new ArrayList<>();
        ArrayList<String> unremB1Studs = new ArrayList<>();
        ArrayList<String> unremB2Studs = new ArrayList<>();
        ArrayList<String> unremInterStuds = new ArrayList<>();
        String firstScheduleStr = "";
        String b1ScheduleStr = "";
        String b2ScheduleStr = "";
        String interScheduleStr = "";
        String cutString;
        String cutStringI = "";
        String noComma;
        String errStudString = "";
        String errSString;
        String errStudNoComma;
        String errInstString = "";
        String errIString;
        String errInstNoComma;
        String firstScheduleString;
        String b1ScheduleString;
        String b2ScheduleString;
        String interScheduleString;

        for (x = 0; x < availableInsts.size(); ++x) {
            levelStart = (availableInsts.get(x).indexOf('|') + 2);
            if (availableInsts.get(x).substring(levelStart).equals("First")) {
                firstInst.add(availableInsts.get(x));
            } else if (availableInsts.get(x).substring(levelStart).equals("Beginner 1")) {
                b1Inst.add(availableInsts.get(x));
            } else if (availableInsts.get(x).substring(levelStart).equals("Beginner 2")) {
                b2Inst.add(availableInsts.get(x));
            } else {
                interInst.add(availableInsts.get(x));
            }
        }
        for (x = 0; x < availableStuds.size(); ++x) {
            levelStart = (availableStuds.get(x).indexOf('|') + 2);
            if (availableStuds.get(x).substring(levelStart).equals("First")) {
                cutString = availableStuds.get(x).replace(availableStuds.get(x).substring(levelStart - 3), "");
                firstStud.add(cutString);
            } else if (availableStuds.get(x).substring(levelStart).equals("Beginner 1")) {
                cutString = availableStuds.get(x).replace(availableStuds.get(x).substring(levelStart - 3), "");
                b1Stud.add(cutString);
            } else if (availableStuds.get(x).substring(levelStart).equals("Beginner 2")) {
                cutString = availableStuds.get(x).replace(availableStuds.get(x).substring(levelStart - 3), "");
                b2Stud.add(cutString);
            } else {
                cutString = availableStuds.get(x).replace(availableStuds.get(x).substring(levelStart - 3), "");
                interStud.add(cutString);
            }
        } //end of calculateSchedule method

        //first start
        while (firstStud.size() != 0) {
            for (x = 0, z = 0; z < studPerInst && x < firstStud.size(); ++z) {
                firstScheduleStr += firstStud.get(x) + ", ";
                if (firstInst.size() == 0 && firstStud.size() > 0)
                    unremFirstStuds.add(firstStud.get(x));
                firstStud.remove(x);
            }
            if (firstInst.size() > 0) {
                firstScheduleString = firstInst.get(y) + ": " + firstScheduleStr + System.lineSeparator() + System.lineSeparator();
                noComma = firstScheduleString.replace(firstScheduleString.substring(firstScheduleString.length() - 4), " " + System.lineSeparator() + System.lineSeparator());
                scheduleStrings.add(noComma);
                firstInst.remove(y);
                firstScheduleStr = "";
            }
        }
        if (unremFirstStuds.size() > 0) {
            for (x = 0; x < unremFirstStuds.size(); ++x)
                errStudString += unremFirstStuds.get(x) + ", ";
            errStudNoComma = errStudString.substring(0, errStudString.length()-2);
            errSString = "\"First\" class is full. Omitted students: " + errStudNoComma + System.lineSeparator();
            schedTA1.append(errSString);
        }
        if (firstInst.size() > 0) {
            for (x = 0; x < firstInst.size(); ++x) {
                levelStart = (firstInst.get(x).indexOf('|') + 2);
                cutStringI = firstInst.get(x).replace(firstInst.get(x).substring(levelStart - 3), "");
                cutFirstInsts.add(cutStringI);
            }

            for (x = 0; x < cutFirstInsts.size(); ++x)
                errInstString += cutFirstInsts.get(x) + ", ";
            errInstNoComma = errInstString.substring(0, errInstString.length() - 2);
            errIString = "\"First\" class does not have enough students. Omitted instructors: " + errInstNoComma + System.lineSeparator();
            schedTA1.append(errIString);
        }

        //beginner1 start
        errStudString = "";
        errInstString = "";
        y = 0;
        while (b1Stud.size() != 0) {
            for (x = 0, z = 0; z < studPerInst && x < b1Stud.size(); ++z) {
                b1ScheduleStr += b1Stud.get(x) + ", ";
                if (b1Inst.size() == 0 && b1Stud.size() > 0)
                    unremB1Studs.add(b1Stud.get(x));
                b1Stud.remove(x);
            }
            if (b1Inst.size() > 0) {
                b1ScheduleString = b1Inst.get(y) + ": " + b1ScheduleStr + System.lineSeparator() + System.lineSeparator();
                noComma = b1ScheduleString.replace(b1ScheduleString.substring(b1ScheduleString.length() - 4), " " + System.lineSeparator() + System.lineSeparator());
                scheduleStrings.add(noComma);
                b1Inst.remove(y);
                b1ScheduleStr = "";
            }
        }
        if (unremB1Studs.size() > 0) {
            for (x = 0; x < unremB1Studs.size(); ++x)
                errStudString += unremB1Studs.get(x) + ", ";
            errStudNoComma = errStudString.substring(0, errStudString.length()-2);
            errSString = "\"Beginner 1\" class is full. Omitted students: " + errStudNoComma + System.lineSeparator();
            schedTA1.append(errSString);
        }
        if (b1Inst.size() > 0) {
            for (x = 0; x < b1Inst.size(); ++x) {
                levelStart = (b1Inst.get(x).indexOf('|') + 2);
                cutStringI = b1Inst.get(x).replace(b1Inst.get(x).substring(levelStart - 3), "");
                cutB1Insts.add(cutStringI);
            }

            for (x = 0; x < cutB1Insts.size(); ++x)
                errInstString += cutB1Insts.get(x) + ", ";
            errInstNoComma = errInstString.substring(0, errInstString.length() - 2);
            errIString = "\"Beginner 1\" class does not have enough students. Omitted instructors: " + errInstNoComma + System.lineSeparator();
            schedTA1.append(errIString);
        }

        //beginner2 start
        errStudString = "";
        errInstString = "";
        y = 0;
        while (b2Stud.size() != 0) {
            for (x = 0, z = 0; z < studPerInst && x < b2Stud.size(); ++z) {
                b2ScheduleStr += b2Stud.get(x) + ", ";
                if (b2Inst.size() == 0 && b2Stud.size() > 0)
                    unremB2Studs.add(b2Stud.get(x));
                b2Stud.remove(x);
            }
            if (b2Inst.size() > 0) {
                b2ScheduleString = b2Inst.get(y) + ": " + b2ScheduleStr + System.lineSeparator() + System.lineSeparator();
                noComma = b2ScheduleString.replace(b2ScheduleString.substring(b2ScheduleString.length() - 4), " " + System.lineSeparator() + System.lineSeparator());
                scheduleStrings.add(noComma);
                b2Inst.remove(y);
                b2ScheduleStr = "";
            }
        }
        if (unremB2Studs.size() > 0) {
            for (x = 0; x < unremB2Studs.size(); ++x)
                errStudString += unremB2Studs.get(x) + ", ";
            errStudNoComma = errStudString.substring(0, errStudString.length()-2);
            errSString = "\"Beginner 2\" class is full. Omitted students: " + errStudNoComma + System.lineSeparator();
            schedTA1.append(errSString);
        }
        if (b2Inst.size() > 0) {
            for (x = 0; x < b2Inst.size(); ++x) {
                levelStart = (b2Inst.get(x).indexOf('|') + 2);
                cutStringI = b2Inst.get(x).replace(b2Inst.get(x).substring(levelStart - 3), "");
                cutB2Insts.add(cutStringI);
            }

            for (x = 0; x < cutB2Insts.size(); ++x)
                errInstString += cutB2Insts.get(x) + ", ";
            errInstNoComma = errInstString.substring(0, errInstString.length() - 2);
            errIString = "\"Beginner 2\" class does not have enough students. Omitted instructors: " + errInstNoComma + System.lineSeparator();
            schedTA1.append(errIString);
        }

        //intermediate start
        errStudString = "";
        errInstString = "";
        y = 0;
        while (interStud.size() != 0) {
            for (x = 0, z = 0; z < studPerInst && x < interStud.size(); ++z) {
                interScheduleStr += interStud.get(x) + ", ";
                if (interInst.size() == 0 && interStud.size() > 0)
                    unremInterStuds.add(interStud.get(x));
                interStud.remove(x);
            }
            if (interInst.size() > 0) {
                interScheduleString = interInst.get(y) + ": " + interScheduleStr + System.lineSeparator() + System.lineSeparator();
                noComma = interScheduleString.replace(interScheduleString.substring(interScheduleString.length() - 4), " " + System.lineSeparator() + System.lineSeparator());
                scheduleStrings.add(noComma);
                interInst.remove(y);
                interScheduleStr = "";
            }
        }
        if (unremInterStuds.size() > 0) {
            for (x = 0; x < unremInterStuds.size(); ++x)
                errStudString += unremInterStuds.get(x) + ", ";
            errStudNoComma = errStudString.substring(0, errStudString.length()-2);
            errSString = "\"Intermediate\" class is full. Omitted students: " + errStudNoComma + System.lineSeparator();
            schedTA1.append(errSString);
        }
        if (interInst.size() > 0) {
            for (x = 0; x < interInst.size(); ++x) {
                levelStart = (interInst.get(x).indexOf('|') + 2);
                cutStringI = interInst.get(x).replace(interInst.get(x).substring(levelStart - 3), "");
                cutInterInsts.add(cutStringI);
            }

            for (x = 0; x < cutInterInsts.size(); ++x)
                errInstString += cutInterInsts.get(x) + ", ";
            errInstNoComma = errInstString.substring(0, errInstString.length() - 2);
            errIString = "\"Intermediate\" class does not have enough students. Omitted instructors: " + errInstNoComma + System.lineSeparator();
            schedTA1.append(errIString);
        }

        for (x = 0; x < scheduleStrings.size(); ++x) {
            scheduleStrings.set(x, scheduleStrings.get(x).replace("[", ""));
            scheduleStrings.set(x, scheduleStrings.get(x).replace("]", ""));
            schedTA1.append(scheduleStrings.get(x));
        }
    } //end of calculateSchedule method


    public void sortInstLevels() {
        //declare variables
        int levelStart1;
        int levelStart2;
        boolean switched;
        String temp;

        for (int x = 0; x < instructorList.size() - 1; ++x) {
            switched = false;
            levelStart1 = instructorList.get(x).indexOf("|") + 2;
            levelStart2 = instructorList.get(x + 1).indexOf("|") + 2;
            if (instructorList.get(x).substring(levelStart1).equals("Intermediate") &&
                    instructorList.get(x + 1).substring(levelStart2).equals("First") || instructorList.get(x).substring(levelStart1).equals("Intermediate")
                    && instructorList.get(x + 1).substring(levelStart2).equals("Beginner 1")
                    || instructorList.get(x).substring(levelStart1).equals("Intermediate") && instructorList.get(x + 1).substring(levelStart2).equals("Beginner 2")) {
                temp = instructorList.get(x);
                instructorList.set(x, instructorList.get(x + 1));
                instructorList.set(x + 1, temp);
                switched = true;
            } else if (instructorList.get(x).substring(levelStart1).equals("Beginner 2") && instructorList.get(x + 1).substring(levelStart2).equals("First") ||
                    instructorList.get(x).substring(levelStart1).equals("Beginner 2") && instructorList.get(x + 1).substring(levelStart2).equals("Beginner 1")) {
                temp = instructorList.get(x);
                instructorList.set(x, instructorList.get(x + 1));
                instructorList.set(x + 1, temp);
                switched = true;
            } else if (instructorList.get(x).substring(levelStart1).equals("Beginner 1") && instructorList.get(x + 1).substring(levelStart2).equals("First")) {
                temp = instructorList.get(x);
                instructorList.set(x, instructorList.get(x + 1));
                instructorList.set(x + 1, temp);
                switched = true;
            }
            if (switched)
                x = -1;
        }
    } //end of sortInstLevels method

    public void sortStudLevels() {
        //declare variables
        int levelStart1;
        int levelStart2;
        boolean switched;
        String temp;

        for (int x = 0; x < studentList.size() - 1; ++x) {
            switched = false;
            levelStart1 = studentList.get(x).indexOf("|") + 2;
            levelStart2 = studentList.get(x + 1).indexOf("|") + 2;
            if (studentList.get(x).substring(levelStart1).equals("Intermediate") &&
                    studentList.get(x + 1).substring(levelStart2).equals("First") || studentList.get(x).substring(levelStart1).equals("Intermediate")
                    && studentList.get(x + 1).substring(levelStart2).equals("Beginner 1")
                    || studentList.get(x).substring(levelStart1).equals("Intermediate") && studentList.get(x + 1).substring(levelStart2).equals("Beginner 2")) {
                temp = studentList.get(x);
                studentList.set(x, studentList.get(x + 1));
                studentList.set(x + 1, temp);
                switched = true;
            } else if (studentList.get(x).substring(levelStart1).equals("Beginner 2") && studentList.get(x + 1).substring(levelStart2).equals("First") ||
                    studentList.get(x).substring(levelStart1).equals("Beginner 2") && studentList.get(x + 1).substring(levelStart2).equals("Beginner 1")) {
                temp = studentList.get(x);
                studentList.set(x, studentList.get(x + 1));
                studentList.set(x + 1, temp);
                switched = true;
            } else if (studentList.get(x).substring(levelStart1).equals("Beginner 1") && studentList.get(x + 1).substring(levelStart2).equals("First")) {
                temp = studentList.get(x);
                studentList.set(x, studentList.get(x + 1));
                studentList.set(x + 1, temp);
                switched = true;
            }
            if (switched)
                x = -1;
        }
    } //end of sortStudLevels method

    public static void main(String[] args) {
        final int WIDTH = 600;
        final int HEIGHT = 75;

        InstructorAssignment iA1 = new InstructorAssignment();
        iA1.setSize(WIDTH, HEIGHT);
    } //end of main method
}
