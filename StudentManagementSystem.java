import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

class Student {
	private int ID;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;

	public Student() {
		ID = 1000; // never used but offset to eliminate problems
		firstName = "";
		lastName = "";
		address = "";
		city = "";
		state = "";
	}

	public Student(String firstName, String lastName, String address, String city, String state) {// to create object
		this.ID = 1000; // never used but offset to eliminate problems
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public Student(int ID, String firstName, String lastName, String address, String city, String state) {// to set
																											// correct
																											// ID number
																											// //
																											// reconstruct
																											// // object
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public int getID() {
		return this.ID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void printStudent() {
		System.out.println("Student ID: " + this.ID);
		System.out.println("First: " + this.firstName);
		System.out.println("Last: " + this.lastName);
		System.out.println("Address: " + this.address);
		System.out.println("City: " + this.city);
		System.out.println("State: " + this.state);
	}
}

class StudentFile {
	private final int RECORD_SIZE = 168; // ID = 4, firstName = 30, lastName = 40, address = 60, city = 30, state = 4
	private RandomAccessFile studentFile;

	public StudentFile(String filename) throws FileNotFoundException {
		try {
			studentFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing student file " + filename);
		}
	}

	public void writeStudentFile(Student temp) throws IOException {
		studentFile.writeInt(temp.getID()); // write the ID (4 bytes)
		String firstName = temp.getFirstName();
		if (firstName.length() > 15) { // write the first name (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				studentFile.writeChar(firstName.charAt(i));
		} else {
			studentFile.writeChars(firstName);
			for (int i = 0; i < 15 - firstName.length(); i++)
				studentFile.writeChar(' ');
		}

		String lastName = temp.getLastName();
		if (lastName.length() > 20) { // write the last name , (20 chars, 40 bytes)
			for (int i = 0; i < 20; i++)
				studentFile.writeChar(lastName.charAt(i));
		} else {
			studentFile.writeChars(lastName);
			for (int i = 0; i < 20 - lastName.length(); i++)
				studentFile.writeChar(' ');
		}

		String address = temp.getAddress();
		if (address.length() > 30) { // write the address, (30 chars, 60 bytes)
			for (int i = 0; i < 30; i++)
				studentFile.writeChar(address.charAt(i));
		} else {
			studentFile.writeChars(address);
			for (int i = 0; i < 30 - address.length(); i++)
				studentFile.writeChar(' ');
		}

		String city = temp.getCity();
		if (city.length() > 15) { // write the city, (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				studentFile.writeChar(city.charAt(i));
		} else {
			studentFile.writeChars(city);
			for (int i = 0; i < 15 - city.length(); i++)
				studentFile.writeChar(' ');
		}

		String state = temp.getState();
		if (state.length() > 2) { // write the state, (2 chars, 4 bytes)
			for (int i = 0; i < 2; i++)
				studentFile.writeChar(state.charAt(i));
		} else {
			studentFile.writeChars(state);
			for (int i = 0; i < 2 - state.length(); i++)
				studentFile.writeChar(' ');
		}
	}

	public Student readStudentFile() throws IOException {
		int ID = studentFile.readInt(); // extract ID

		char[] firstNameCharArray = new char[15]; // extract firstName
		for (int i = 0; i < 15; i++)
			firstNameCharArray[i] = studentFile.readChar();
		String firstName = new String(firstNameCharArray);
		firstName.trim();

		char[] lastNameCharArray = new char[20]; // extract lastName
		for (int i = 0; i < 20; i++)
			lastNameCharArray[i] = studentFile.readChar();
		String lastName = new String(lastNameCharArray);
		lastName.trim();

		char[] addressCharArray = new char[30]; // extract address
		for (int i = 0; i < 30; i++)
			addressCharArray[i] = studentFile.readChar();
		String address = new String(addressCharArray);
		address.trim();

		char[] cityCharArray = new char[15]; // extract city
		for (int i = 0; i < 15; i++)
			cityCharArray[i] = studentFile.readChar();
		String city = new String(cityCharArray);
		city.trim();

		char[] stateCharArray = new char[2]; // extract state
		for (int i = 0; i < 2; i++)
			stateCharArray[i] = studentFile.readChar();
		String state = new String(stateCharArray);
		state.trim();

		Student temp = new Student(ID, firstName, lastName, address, city, state);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		studentFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return studentFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		studentFile.close();
	}
}

class Course {
	private int CNUM;
	private String CID;
	private String cName;
	private String instructor;
	private String department;

	public Course() {
		this.CNUM = 1000; // never used but offset to eliminate possible issues
		this.CID = "";
		this.cName = "";
		this.instructor = "";
		this.department = "";
	}

	public Course(String CID, String cName, String instructor, String department) { // to create object
		this.CNUM = 1000; // never used but offset to eliminate potential problems
		this.CID = CID;
		this.cName = cName;
		this.instructor = instructor;
		this.department = department;
	}

	public Course(int CNUM, String CID, String cName, String instructor, String department) { // to reconstruct objet
		this.CNUM = CNUM;
		this.CID = CID;
		this.cName = cName;
		this.instructor = instructor;
		this.department = department;
	}

	public int getCNUM() {
		return this.CNUM;
	}

	public String getCID() {
		return this.CID;
	}

	public String getcName() {
		return this.cName;
	}

	public String getInstructor() {
		return this.instructor;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setCID(String CID) {
		this.CID = CID;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void printCourse() {
		System.out.println("CNUM: " + this.CNUM);
		System.out.println("CID: " + this.CID);
		System.out.println("Course Name: " + this.cName);
		System.out.println("Instructor: " + this.instructor);
		System.out.println("Department: " + this.department);
	}
}

class CourseFile {
	private final int RECORD_SIZE = 110; // CNUM = 4, CID = 12, cName = 24, instructor = 30, department = 40
	private RandomAccessFile courseFile;

	public CourseFile(String filename) throws FileNotFoundException {
		try {
			courseFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing course file " + filename);
		}
	}

	public void writeCourseFile(Course temp) throws IOException {
		courseFile.writeInt(temp.getCNUM()); // write the CNUM (4 bytes)
		String CID = temp.getCID();
		if (CID.length() > 6) { // write the Course ID (6 chars, 12 bytes)
			for (int i = 0; i < 6; i++)
				courseFile.writeChar(CID.charAt(i));
		} else {
			courseFile.writeChars(CID);
			for (int i = 0; i < 6 - CID.length(); i++)
				courseFile.writeChar(' ');
		}

		String cName = temp.getcName();
		if (cName.length() > 12) { // write the course name , (12 chars, 24 bytes)
			for (int i = 0; i < 12; i++)
				courseFile.writeChar(cName.charAt(i));
		} else {
			courseFile.writeChars(cName);
			for (int i = 0; i < 12 - cName.length(); i++)
				courseFile.writeChar(' ');
		}

		String instructor = temp.getInstructor();
		if (instructor.length() > 15) { // write the instructor, (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				courseFile.writeChar(instructor.charAt(i));
		} else {
			courseFile.writeChars(instructor);
			for (int i = 0; i < 15 - instructor.length(); i++)
				courseFile.writeChar(' ');
		}

		String department = temp.getDepartment();
		if (department.length() > 20) { // write the department, (20 chars, 40 bytes)
			for (int i = 0; i < 20; i++)
				courseFile.writeChar(department.charAt(i));
		} else {
			courseFile.writeChars(department);
			for (int i = 0; i < 20 - department.length(); i++)
				courseFile.writeChar(' ');
		}

	}

	public Course readCourseFile() throws IOException {
		int CNUM = courseFile.readInt(); // extract CNUM

		char[] CIDCharArray = new char[6]; // extract Course ID
		for (int i = 0; i < 6; i++)
			CIDCharArray[i] = courseFile.readChar();
		String CID = new String(CIDCharArray);
		CID.trim();

		char[] cNameCharArray = new char[12]; // extract Course Name
		for (int i = 0; i < 12; i++)
			cNameCharArray[i] = courseFile.readChar();
		String cName = new String(cNameCharArray);
		cName.trim();

		char[] InstructorCharArray = new char[15]; // extract instructor
		for (int i = 0; i < 15; i++)
			InstructorCharArray[i] = courseFile.readChar();
		String instructor = new String(InstructorCharArray);
		instructor.trim();

		char[] DepartmentCharArray = new char[20]; // extract department
		for (int i = 0; i < 20; i++)
			DepartmentCharArray[i] = courseFile.readChar();
		String department = new String(DepartmentCharArray);
		department.trim();

		Course temp = new Course(CNUM, CID, cName, instructor, department);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		courseFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return courseFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		courseFile.close();
	}

}

class Enrollment {
	private int enrollmentID;
	private int studentID;
	private int cNum;
	private String year;
	private String semester;
	private String grade;

	public Enrollment() {
		this.studentID = 0;
		this.cNum = 0;
		this.year = "";
		this.semester = "";
		this.grade = "";
	}

	public Enrollment(int studentID, int cNum, String year, String semester, String grade) {
		this.enrollmentID = 1000; // never used offset to avoid a potential problem
		this.studentID = studentID;
		this.cNum = cNum;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}

	public Enrollment(int enrollmentID, int studentID, int cNum, String year, String semester, String grade) {
		this.enrollmentID = enrollmentID;
		this.studentID = studentID;
		this.cNum = cNum;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}

	public int getStudentID() {
		return this.studentID;
	}

	public int getcNum() {
		return this.cNum;
	}

	public int getEID() {
		return this.enrollmentID;
	}

	public String getYear() {
		return this.year;
	}

	public String getSemester() {
		return this.semester;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public void setcNum(int cNum) {
		this.cNum = cNum;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void printEnrollment() {
		System.out.println("Enrollment ID: " + this.enrollmentID);
		System.out.println("StudentID: " + this.studentID);
		System.out.println("Course Number: " + this.cNum);
		System.out.println("Year: " + this.year);
		System.out.println("Semester: " + this.semester);
		System.out.println("Grade: " + this.grade);
	}

}

class EnrollmentFile {
	private final int RECORD_SIZE = 38; // BYTES Enrollment ID = 4, Student ID = 4, cNum = 4, year = 8, semester = 16,
										// grade = 2
	private RandomAccessFile enrollmentFile;

	public EnrollmentFile(String filename) throws FileNotFoundException {
		try {
			enrollmentFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing enrollment file " + filename);
		}
	}

	public void writeEnrollmentFile(Enrollment temp) throws IOException {
		enrollmentFile.writeInt(temp.getEID()); // write the enrollment ID (4 bytes)
		enrollmentFile.writeInt(temp.getStudentID()); // write the Student ID (4 bytes)
		enrollmentFile.writeInt(temp.getcNum()); // write the course number (4 bytes)
		String year = temp.getYear();
		if (year.length() > 4) { // write the year (4 chars, 8 bytes)
			for (int i = 0; i < 4; i++)
				enrollmentFile.writeChar(year.charAt(i));
		} else {
			enrollmentFile.writeChars(year);
			for (int i = 0; i < 4 - year.length(); i++)
				enrollmentFile.writeChar(' ');
		}

		String semester = temp.getSemester();
		if (semester.length() > 8) { // write the semester , (8 chars, 16 bytes)
			for (int i = 0; i < 8; i++)
				enrollmentFile.writeChar(semester.charAt(i));
		} else {
			enrollmentFile.writeChars(semester);
			for (int i = 0; i < 8 - semester.length(); i++)
				enrollmentFile.writeChar(' ');
		}

		String grade = temp.getGrade();
		if (grade.length() > 1) { // write the instructor, (15 chars, 30 bytes)
			for (int i = 0; i < 1; i++)
				enrollmentFile.writeChar(grade.charAt(i));
		} else {
			enrollmentFile.writeChars(grade);
			for (int i = 0; i < 1 - grade.length(); i++)
				enrollmentFile.writeChar(' ');
		}

	}

	public Enrollment readEnrollmentFile() throws IOException {
		int enrollmentID = enrollmentFile.readInt(); // extract enrollment ID
		int studentID = enrollmentFile.readInt(); // extract student ID
		int cNum = enrollmentFile.readInt(); // extract course number
		char[] yearCharArray = new char[4]; // extract year field
		for (int i = 0; i < 4; i++)
			yearCharArray[i] = enrollmentFile.readChar();
		String year = new String(yearCharArray);
		year.trim();

		char[] semesterCharArray = new char[8]; // extract semester
		for (int i = 0; i < 8; i++)
			semesterCharArray[i] = enrollmentFile.readChar();
		String semester = new String(semesterCharArray);
		semester.trim();

		char[] gradeCharArray = new char[1]; // extract grade
		for (int i = 0; i < 1; i++)
			gradeCharArray[i] = enrollmentFile.readChar();
		String grade = new String(gradeCharArray);
		grade.trim();

		Enrollment temp = new Enrollment(enrollmentID, studentID, cNum, year, semester, grade);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		enrollmentFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return enrollmentFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		enrollmentFile.close();
	}

	public int readInt() throws IOException {
		return enrollmentFile.readInt();
	}
}

class StudentAddPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5149000693352260886L;
	private JLabel studentAdd_label;
	private JLabel studentAdd_firstNameLabel;
	private JLabel studentAdd_lastNameLabel;
	private JLabel studentAdd_addressLabel;
	private JLabel studentAdd_cityLabel;
	private JLabel studentAdd_stateLabel;
	private JTextField studentAdd_firstNameText;
	private JTextField studentAdd_lastNameText;
	private JTextField studentAdd_addressText;
	private JTextField studentAdd_cityText;
	private JTextField studentAdd_stateText;

	public String getFirstName() {
		return studentAdd_firstNameText.getText();
	}

	public String getLastName() {
		return studentAdd_lastNameText.getText();
	}

	public String getAddress() {
		return studentAdd_addressText.getText();
	}

	public String getCity() {
		return studentAdd_cityText.getText();
	}

	public String getState() {
		return studentAdd_stateText.getText();
	}

	public StudentAddPanel() throws FileNotFoundException {
		buildStudentAddPanel();
	}

	public void buildStudentAddPanel() {
		setLayout(new GridLayout(7, 1));

		JPanel label_JPanel = new JPanel();
		studentAdd_label = new JLabel("Add Student");
		label_JPanel.add(studentAdd_label);

		JPanel firstName_JPanel = new JPanel();
		studentAdd_firstNameLabel = new JLabel("First Name");
		studentAdd_firstNameText = new JTextField(15);
		firstName_JPanel.add(studentAdd_firstNameLabel);
		firstName_JPanel.add(studentAdd_firstNameText);

		JPanel lastName_JPanel = new JPanel();
		studentAdd_lastNameLabel = new JLabel("Last Name");
		studentAdd_lastNameText = new JTextField(20);
		lastName_JPanel.add(studentAdd_lastNameLabel);
		lastName_JPanel.add(studentAdd_lastNameText);

		JPanel address_JPanel = new JPanel();
		studentAdd_addressLabel = new JLabel("Address");
		studentAdd_addressText = new JTextField(30);
		address_JPanel.add(studentAdd_addressLabel);
		address_JPanel.add(studentAdd_addressText);

		JPanel city_JPanel = new JPanel();
		studentAdd_cityLabel = new JLabel("City");
		studentAdd_cityText = new JTextField(15);
		city_JPanel.add(studentAdd_cityLabel);
		city_JPanel.add(studentAdd_cityText);

		JPanel state_JPanel = new JPanel();
		studentAdd_stateLabel = new JLabel("State");
		studentAdd_stateText = new JTextField(2);
		state_JPanel.add(studentAdd_stateLabel);
		state_JPanel.add(studentAdd_stateText);

		add(label_JPanel);
		add(firstName_JPanel);
		add(lastName_JPanel);
		add(address_JPanel);
		add(city_JPanel);
		add(state_JPanel);
	}

	public Student createStudent(int numberOfStudentsInFile) {
		String firstName;
		String lastName;
		String address;
		String city;
		String state;

		firstName = studentAdd_firstNameText.getText();
		lastName = studentAdd_lastNameText.getText();
		address = studentAdd_addressText.getText();
		city = studentAdd_cityText.getText();
		state = studentAdd_stateText.getText();

		Student newStudent = new Student(numberOfStudentsInFile, firstName, lastName, address, city, state);
		return newStudent;

	}

	public void studentAddDisplayPanel() {

		setLayout(new GridLayout(2, 1));

		JPanel label_JPanel1 = new JPanel();
		studentAdd_label = new JLabel("Student Details");
		label_JPanel1.add(studentAdd_label);

		JPanel firstName_JPanel1 = new JPanel();
		studentAdd_firstNameText.setEditable(false);
		firstName_JPanel1.add(studentAdd_firstNameLabel);
		firstName_JPanel1.add(studentAdd_firstNameText);

		add(label_JPanel1);
		add(firstName_JPanel1);
		validate();
		setVisible(true);

	}
}

class CourseAddPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel courseAdd_label;
	private JLabel courseAdd_CIDLabel;
	private JLabel courseAdd_cNameLabel;
	private JLabel courseAdd_instructorLabel;
	private JLabel courseAdd_departmentLabel;

	private JTextField courseAdd_CIDText;
	private JTextField courseAdd_cNameText;
	private JTextField courseAdd_instructorText;
	private JTextField courseAdd_departmentText;

	public String getCID() {
		return courseAdd_CIDText.getText();
	}

	public String getcName() {
		return courseAdd_cNameText.getText();
	}

	public String getInstructor() {
		return courseAdd_instructorText.getText();
	}

	public String getDepartment() {
		return courseAdd_departmentText.getText();
	}

	public CourseAddPanel() throws FileNotFoundException {
		buildCourseAddPanel();
	}

	public void buildCourseAddPanel() {
		setLayout(new GridLayout(7, 1));

		JPanel label_JPanel5 = new JPanel();
		courseAdd_label = new JLabel("Add Course");
		label_JPanel5.add(courseAdd_label);

		JPanel firstName_JPanel5 = new JPanel();
		courseAdd_CIDLabel = new JLabel("Course ID");
		courseAdd_CIDText = new JTextField(6);
		firstName_JPanel5.add(courseAdd_CIDLabel);
		firstName_JPanel5.add(courseAdd_CIDText);

		JPanel lastName_JPanel5 = new JPanel();
		courseAdd_cNameLabel = new JLabel("Course Name");
		courseAdd_cNameText = new JTextField(12);
		lastName_JPanel5.add(courseAdd_cNameLabel);
		lastName_JPanel5.add(courseAdd_cNameText);

		JPanel address_JPanel5 = new JPanel();
		courseAdd_instructorLabel = new JLabel("Instructor");
		courseAdd_instructorText = new JTextField(15);
		address_JPanel5.add(courseAdd_instructorLabel);
		address_JPanel5.add(courseAdd_instructorText);

		JPanel city_JPanel5 = new JPanel();
		courseAdd_departmentLabel = new JLabel("Department");
		courseAdd_departmentText = new JTextField(20);
		city_JPanel5.add(courseAdd_departmentLabel);
		city_JPanel5.add(courseAdd_departmentText);

		add(label_JPanel5);
		add(firstName_JPanel5);
		add(lastName_JPanel5);
		add(address_JPanel5);
		add(city_JPanel5);

	}

	public Course createCourse(int numberOfCoursesInFile) { // THIS CLASS WAS MOVED TO SUPER CLASS
		String CID;
		String cName;
		String instructor;
		String department;

		CID = courseAdd_CIDText.getText();
		cName = courseAdd_cNameText.getText();
		instructor = courseAdd_instructorText.getText();
		department = courseAdd_departmentText.getText();

		Course newCourse = new Course(numberOfCoursesInFile, CID, cName, instructor, department);
		return newCourse;

	}
}

class CourseDisplayPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Course course;
	private JLabel courseAfterAdd_label;
	private JLabel courseAfterAdd_CIDLabel;
	private JLabel courseAfterAdd_cNameLabel;
	private JLabel courseAfterAdd_instructorLabel;
	private JLabel courseAfterAdd_departmentLabel;

	private JTextField courseAfterAdd_CIDText;
	private JTextField courseAfterAdd_cNameText;
	private JTextField courseAfterAdd_instructorText;
	private JTextField courseAfterAdd_departmentText;

	private JLabel courseAfterAdd_cNUMLabel;
	private JTextField courseAfterAdd_cNUMText;

	public CourseDisplayPanel(Course temp) {
		this.course = temp;
		buildCourseDisplayPanel();
	}

	public void buildCourseDisplayPanel() {
		setLayout(new GridLayout(7, 1));

		JPanel label_JPanel = new JPanel();
		courseAfterAdd_label = new JLabel("Display Course");
		label_JPanel.add(courseAfterAdd_label);

		JPanel city_JPanel = new JPanel();
		courseAfterAdd_cNUMLabel = new JLabel("cNUM");
		String intToStringCNUM = String.valueOf(course.getCNUM());
		courseAfterAdd_cNUMText = new JTextField(intToStringCNUM);
		courseAfterAdd_cNUMText.setEditable(false);
		city_JPanel.add(courseAfterAdd_cNUMLabel);
		city_JPanel.add(courseAfterAdd_cNUMText);

		JPanel ID_JPanel = new JPanel();
		courseAfterAdd_CIDLabel = new JLabel("Course ID");
		String intToString = String.valueOf(course.getCID());
		courseAfterAdd_CIDText = new JTextField(intToString);
		courseAfterAdd_CIDText.setEditable(false);
		ID_JPanel.add(courseAfterAdd_CIDLabel);
		ID_JPanel.add(courseAfterAdd_CIDText);

		JPanel firstName_JPanel = new JPanel();
		courseAfterAdd_cNameLabel = new JLabel("Course Name");
		courseAfterAdd_cNameText = new JTextField(course.getcName());
		courseAfterAdd_cNameText.setEditable(false);
		firstName_JPanel.add(courseAfterAdd_cNameLabel);
		firstName_JPanel.add(courseAfterAdd_cNameText);

		JPanel lastName_JPanel = new JPanel();
		courseAfterAdd_instructorLabel = new JLabel("Instructor");
		courseAfterAdd_instructorText = new JTextField(course.getInstructor());
		courseAfterAdd_instructorText.setEditable(false);
		lastName_JPanel.add(courseAfterAdd_instructorLabel);
		lastName_JPanel.add(courseAfterAdd_instructorText);

		JPanel address_JPanel = new JPanel();
		courseAfterAdd_departmentLabel = new JLabel("Department");
		courseAfterAdd_departmentText = new JTextField(course.getDepartment());
		courseAfterAdd_departmentText.setEditable(false);
		address_JPanel.add(courseAfterAdd_departmentLabel);
		address_JPanel.add(courseAfterAdd_departmentText);

		add(label_JPanel);
		add(city_JPanel);
		add(ID_JPanel);
		add(firstName_JPanel);
		add(lastName_JPanel);
		add(address_JPanel);

	}

}

class StudentDisplayPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Student student;
	private JLabel studentAfterAdd_label;
	private JLabel studentAfterAdd_firstNameLabel;
	private JLabel studentAfterAdd_lastNameLabel;
	private JLabel studentAfterAdd_addressLabel;
	private JLabel studentAfterAdd_cityLabel;
	private JLabel studentAfterAdd_stateLabel;
	private JTextField studentAfterAdd_firstNameText;
	private JTextField studentAfterAdd_lastNameText;
	private JTextField studentAfterAdd_addressText;
	private JTextField studentAfterAdd_cityText;
	private JTextField studentAfterAdd_stateText;
	private JLabel studentAfterAdd_IDLabel;
	private JTextField studentAfterAdd_IDText;

	public StudentDisplayPanel(Student temp) {
		this.student = temp;
		buildStudentDisplayPanel();
	}

	public void buildStudentDisplayPanel() {
		setLayout(new GridLayout(7, 1));

		JPanel label_JPanel = new JPanel();
		studentAfterAdd_label = new JLabel("Display Student");
		label_JPanel.add(studentAfterAdd_label);

		JPanel ID_JPanel = new JPanel();
		studentAfterAdd_IDLabel = new JLabel("Student ID");
		String intToString = String.valueOf(student.getID());
		studentAfterAdd_IDText = new JTextField(intToString);
		studentAfterAdd_IDText.setEditable(false);
		ID_JPanel.add(studentAfterAdd_IDLabel);
		ID_JPanel.add(studentAfterAdd_IDText);

		JPanel firstName_JPanel = new JPanel();
		studentAfterAdd_firstNameLabel = new JLabel("First Name");
		studentAfterAdd_firstNameText = new JTextField(student.getFirstName());
		studentAfterAdd_firstNameText.setEditable(false);
		firstName_JPanel.add(studentAfterAdd_firstNameLabel);
		firstName_JPanel.add(studentAfterAdd_firstNameText);

		JPanel lastName_JPanel = new JPanel();
		studentAfterAdd_lastNameLabel = new JLabel("Last Name");
		studentAfterAdd_lastNameText = new JTextField(student.getLastName());
		studentAfterAdd_lastNameText.setEditable(false);
		lastName_JPanel.add(studentAfterAdd_lastNameLabel);
		lastName_JPanel.add(studentAfterAdd_lastNameText);

		JPanel address_JPanel = new JPanel();
		studentAfterAdd_addressLabel = new JLabel("Address");
		studentAfterAdd_addressText = new JTextField(student.getAddress());
		studentAfterAdd_addressText.setEditable(false);
		address_JPanel.add(studentAfterAdd_addressLabel);
		address_JPanel.add(studentAfterAdd_addressText);

		JPanel city_JPanel = new JPanel();
		studentAfterAdd_cityLabel = new JLabel("City");
		studentAfterAdd_cityText = new JTextField(student.getCity());
		studentAfterAdd_cityText.setEditable(false);
		city_JPanel.add(studentAfterAdd_cityLabel);
		city_JPanel.add(studentAfterAdd_cityText);

		JPanel state_JPanel = new JPanel();
		studentAfterAdd_stateLabel = new JLabel("State");
		studentAfterAdd_stateText = new JTextField(student.getState());
		studentAfterAdd_stateText.setEditable(false);
		state_JPanel.add(studentAfterAdd_stateLabel);
		state_JPanel.add(studentAfterAdd_stateText);

		add(label_JPanel);
		add(ID_JPanel);
		add(firstName_JPanel);
		add(lastName_JPanel);
		add(address_JPanel);
		add(city_JPanel);
		add(state_JPanel);

	}

}

class StudentSearchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel studentSearch_label;
	private JLabel studentSearch_inputIDLabel;
	private JTextField studentSearch_inputIDText;

	public String getInputStudentIDText() {
		return studentSearch_inputIDText.getText();
	}

	public StudentSearchPanel() throws FileNotFoundException {

		buildStudentSearchPanel();
	}

	public void buildStudentSearchPanel() {
		setLayout(new GridLayout(2, 1));

		JPanel label_JPanel = new JPanel();
		studentSearch_label = new JLabel("Search Student");
		label_JPanel.add(studentSearch_label);

		JPanel enterID_JPanel = new JPanel();
		studentSearch_inputIDLabel = new JLabel("Enter Student ID to search");
		studentSearch_inputIDText = new JTextField(15);
		enterID_JPanel.add(studentSearch_inputIDLabel);
		enterID_JPanel.add(studentSearch_inputIDText);

		add(label_JPanel);
		add(enterID_JPanel);
	}

}

class StudentEditPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Student student;
	private JLabel studentEdit_label;
	private JLabel studentEdit_firstNameLabel;
	private JLabel studentEdit_lastNameLabel;
	private JLabel studentEdit_addressLabel;
	private JLabel studentEdit_cityLabel;
	private JLabel studentEdit_stateLabel;
	private JTextField studentEdit_firstNameText;
	private JTextField studentEdit_lastNameText;
	private JTextField studentEdit_addressText;
	private JTextField studentEdit_cityText;
	private JTextField studentEdit_stateText;

	private JLabel studentEdit_IDLabel;
	private JTextField studentEdit_IDText;

	private JLabel studentEdit_label2; // 2 stands for user input version
	private JLabel studentEdit_firstNameLabel2;
	private JLabel studentEdit_lastNameLabel2;
	private JLabel studentEdit_addressLabel2;
	private JLabel studentEdit_cityLabel2;
	private JLabel studentEdit_stateLabel2;
	private JTextField studentEdit_studentIDText2;
	private JTextField studentEdit_firstNameText2;
	private JTextField studentEdit_lastNameText2;
	private JTextField studentEdit_addressText2;
	private JTextField studentEdit_cityText2;
	private JTextField studentEdit_stateText2;

	private JLabel studentEdit_IDLabel2;
	private JTextField studentEdit_IDText2;

	public String getStudentEdit_studentIDText2() {
		return studentEdit_studentIDText2.getText();
	}

	public String getStudentEdit_firstNameText2() {
		return studentEdit_firstNameText2.getText();
	}

	public String getStudentEdit_lastNameText2() {
		return studentEdit_lastNameText2.getText();
	}

	public String getStudentEdit_addressText2() {
		return studentEdit_addressText2.getText();
	}

	public String getStudentEdit_cityText2() {
		return studentEdit_cityText2.getText();
	}

	public String getStudentEdit_stateText2() {
		return studentEdit_stateText2.getText();
	}

	public String getInputStudentIDText() {
		return studentEdit_IDText.getText();
	}

	public StudentEditPanel(Student temp) throws FileNotFoundException {
		this.student = temp;
		buildStudentEditPanel(student);
	}

	public void buildStudentEditPanel(Student student) {

		JPanel label_JPanel = new JPanel();
		studentEdit_label = new JLabel("[Old Student Data]");
		label_JPanel.add(studentEdit_label);

		JPanel ID_JPanel = new JPanel();
		studentEdit_IDLabel = new JLabel("Student ID");
		String intToString = String.valueOf(student.getID());
		studentEdit_IDText = new JTextField(intToString);
		studentEdit_IDText.setEditable(false);
		ID_JPanel.add(studentEdit_IDLabel);
		ID_JPanel.add(studentEdit_IDText);

		JPanel firstName_JPanel = new JPanel();
		studentEdit_firstNameLabel = new JLabel("First Name");
		studentEdit_firstNameText = new JTextField(student.getFirstName());
		studentEdit_firstNameText.setEditable(false);
		firstName_JPanel.add(studentEdit_firstNameLabel);
		firstName_JPanel.add(studentEdit_firstNameText);

		JPanel lastName_JPanel = new JPanel();
		studentEdit_lastNameLabel = new JLabel("Last Name");
		studentEdit_lastNameText = new JTextField(student.getLastName());
		studentEdit_lastNameText.setEditable(false);
		lastName_JPanel.add(studentEdit_lastNameLabel);
		lastName_JPanel.add(studentEdit_lastNameText);

		JPanel address_JPanel = new JPanel();
		studentEdit_addressLabel = new JLabel("Address");
		studentEdit_addressText = new JTextField(student.getAddress());
		studentEdit_addressText.setEditable(false);
		address_JPanel.add(studentEdit_addressLabel);
		address_JPanel.add(studentEdit_addressText);

		JPanel city_JPanel = new JPanel();
		studentEdit_cityLabel = new JLabel("City");
		studentEdit_cityText = new JTextField(student.getCity());
		studentEdit_cityText.setEditable(false);
		city_JPanel.add(studentEdit_cityLabel);
		city_JPanel.add(studentEdit_cityText);

		JPanel state_JPanel = new JPanel();
		studentEdit_stateLabel = new JLabel("State");
		studentEdit_stateText = new JTextField(student.getState());
		studentEdit_stateText.setEditable(false);
		state_JPanel.add(studentEdit_stateLabel);
		state_JPanel.add(studentEdit_stateText);

		///// DATA 2 input
		JPanel label_JPanel2 = new JPanel();
		studentEdit_label2 = new JLabel("[New Student Data]");
		label_JPanel2.add(studentEdit_label2);

		JPanel ID_JPanel2 = new JPanel();
		studentEdit_IDLabel2 = new JLabel("Student ID");
		String intToString2 = String.valueOf(student.getID());
		studentEdit_IDText2 = new JTextField(intToString2);
		studentEdit_IDText2.setEditable(false);
		ID_JPanel2.add(studentEdit_IDLabel2);
		ID_JPanel2.add(studentEdit_IDText2);

		JPanel firstName_JPanel2 = new JPanel();
		studentEdit_firstNameLabel2 = new JLabel("First Name");
		studentEdit_firstNameText2 = new JTextField(15);
		firstName_JPanel2.add(studentEdit_firstNameLabel2);
		firstName_JPanel2.add(studentEdit_firstNameText2);

		JPanel lastName_JPanel2 = new JPanel();
		studentEdit_lastNameLabel2 = new JLabel("Last Name");
		studentEdit_lastNameText2 = new JTextField(20);
		lastName_JPanel2.add(studentEdit_lastNameLabel2);
		lastName_JPanel2.add(studentEdit_lastNameText2);

		JPanel address_JPanel2 = new JPanel();
		studentEdit_addressLabel2 = new JLabel("Address");
		studentEdit_addressText2 = new JTextField(30);
		address_JPanel2.add(studentEdit_addressLabel2);
		address_JPanel2.add(studentEdit_addressText2);

		JPanel city_JPanel2 = new JPanel();
		studentEdit_cityLabel2 = new JLabel("City");
		studentEdit_cityText2 = new JTextField(15);
		city_JPanel2.add(studentEdit_cityLabel2);
		city_JPanel2.add(studentEdit_cityText2);

		JPanel state_JPanel2 = new JPanel();
		studentEdit_stateLabel2 = new JLabel("State");
		studentEdit_stateText2 = new JTextField(2);
		state_JPanel2.add(studentEdit_stateLabel2);
		state_JPanel2.add(studentEdit_stateText2);
		///////

		JPanel oldEditData = new JPanel();
		oldEditData.setLayout(new GridLayout(7, 1));

		oldEditData.add(label_JPanel);
		oldEditData.add(ID_JPanel);
		oldEditData.add(firstName_JPanel);
		oldEditData.add(lastName_JPanel);
		oldEditData.add(address_JPanel);
		oldEditData.add(city_JPanel);
		oldEditData.add(state_JPanel);

		JPanel newEditData = new JPanel();
		newEditData.setLayout(new GridLayout(7, 1));

		newEditData.add(label_JPanel2);
		newEditData.add(ID_JPanel2);
		newEditData.add(firstName_JPanel2);
		newEditData.add(lastName_JPanel2);
		newEditData.add(address_JPanel2);
		newEditData.add(city_JPanel2);
		newEditData.add(state_JPanel2);

		JLabel editMenuLabel = new JLabel("Edit Student");
		JPanel editMenuLabelPanel = new JPanel();
		editMenuLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		editMenuLabelPanel.add(editMenuLabel);

		setLayout(new BorderLayout());

		add(oldEditData, BorderLayout.WEST);
		add(newEditData, BorderLayout.EAST);
		add(editMenuLabelPanel, BorderLayout.NORTH);

		JLabel imageLabel = new JLabel();
		ImageIcon arrows = new ImageIcon("rightarrow.png");
		imageLabel.setIcon(arrows);

		add(imageLabel, BorderLayout.CENTER);

	}

}

class CourseEditPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Course course;
	private JLabel courseEdit_label;
	private JLabel courseEdit_CIDLabel;
	private JLabel courseEdit_cNameLabel;
	private JLabel courseEdit_instructorLabel;
	private JLabel courseEdit_departmentLabel;

	private JTextField courseEdit_CIDText;
	private JTextField courseEdit_cNameText;
	private JTextField courseEdit_instructorText;
	private JTextField courseEdit_departmentText;

	private JLabel courseEdit_CNUMLabel;
	private JTextField courseEdit_CNUMText;

	// 2 stands for user input version
	private JLabel courseEdit_label2;
	private JLabel courseEdit_CIDLabel2;
	private JLabel courseEdit_cNameLabel2;
	private JLabel courseEdit_instructorLabel2;
	private JLabel courseEdit_departmentLabel2;

	private JTextField courseEdit_CIDText2;
	private JTextField courseEdit_cNameText2;
	private JTextField courseEdit_instructorText2;
	private JTextField courseEdit_departmentText2;

	private JLabel courseEdit_CNUMLabel2;
	private JTextField courseEdit_CNUMText2;

	private JTextField courseEdit_IDText; 

	public String getCourseEdit_CNUMText2() {
		return courseEdit_CNUMText2.getText();
	}

	public String getCourseEdit_CIDText2() {
		return courseEdit_CIDText2.getText();
	}

	public String getCourseEdit_cNameText2() {
		return courseEdit_cNameText2.getText();
	}

	public String getCourseEdit_instructorText2() {
		return courseEdit_instructorText2.getText();
	}

	public String getCourseEdit_departmentText2() {
		return courseEdit_departmentText2.getText();
	}

	public String getInputCourseIDText() {
		return courseEdit_IDText.getText();
	}

	public CourseEditPanel(Course temp) throws FileNotFoundException {
		this.course = temp;
		buildCourseEditPanel(course);
	}

	public void buildCourseEditPanel(Course course) {

		JPanel label_JPanel = new JPanel();
		courseEdit_label = new JLabel("[Old Course Data]");
		label_JPanel.add(courseEdit_label);

		JPanel ID_JPanel = new JPanel();
		courseEdit_CNUMLabel = new JLabel("cNum");
		String intToString = String.valueOf(course.getCNUM());
		courseEdit_CNUMText = new JTextField(intToString);
		courseEdit_CNUMText.setEditable(false);
		ID_JPanel.add(courseEdit_CNUMLabel);
		ID_JPanel.add(courseEdit_CNUMText);

		JPanel firstName_JPanel = new JPanel();
		courseEdit_CIDLabel = new JLabel("Course ID");
		courseEdit_CIDText = new JTextField(course.getCID());
		courseEdit_CIDText.setEditable(false);
		firstName_JPanel.add(courseEdit_CIDLabel);
		firstName_JPanel.add(courseEdit_CIDText);

		JPanel lastName_JPanel = new JPanel();
		courseEdit_cNameLabel = new JLabel("Course Name");
		courseEdit_cNameText = new JTextField(course.getcName());
		courseEdit_cNameText.setEditable(false);
		lastName_JPanel.add(courseEdit_cNameLabel);
		lastName_JPanel.add(courseEdit_cNameText);

		JPanel address_JPanel = new JPanel();
		courseEdit_instructorLabel = new JLabel("Instructor");
		courseEdit_instructorText = new JTextField(course.getInstructor());
		courseEdit_instructorText.setEditable(false);
		address_JPanel.add(courseEdit_instructorLabel);
		address_JPanel.add(courseEdit_instructorText);

		JPanel city_JPanel = new JPanel();
		courseEdit_departmentLabel = new JLabel("Department");
		courseEdit_departmentText = new JTextField(course.getDepartment());
		courseEdit_departmentText.setEditable(false);
		city_JPanel.add(courseEdit_departmentLabel);
		city_JPanel.add(courseEdit_departmentText);

		///// DATA 2 input
		JPanel label_JPanel2 = new JPanel();
		courseEdit_label2 = new JLabel("[New Student Data]");
		label_JPanel2.add(courseEdit_label2);

		JPanel ID_JPanel2 = new JPanel();
		courseEdit_CNUMLabel2 = new JLabel("cNum");
		String intToString2 = String.valueOf(course.getCNUM());
		courseEdit_CNUMText2 = new JTextField(intToString2);
		courseEdit_CNUMText2.setEditable(false);
		ID_JPanel2.add(courseEdit_CNUMLabel2);
		ID_JPanel2.add(courseEdit_CNUMText2);

		JPanel firstName_JPanel2 = new JPanel();
		courseEdit_CIDLabel2 = new JLabel("Course ID");
		courseEdit_CIDText2 = new JTextField(15);
		firstName_JPanel2.add(courseEdit_CIDLabel2);
		firstName_JPanel2.add(courseEdit_CIDText2);

		JPanel lastName_JPanel2 = new JPanel();
		courseEdit_cNameLabel2 = new JLabel("Course Name");
		courseEdit_cNameText2 = new JTextField(20);
		lastName_JPanel2.add(courseEdit_cNameLabel2);
		lastName_JPanel2.add(courseEdit_cNameText2);

		JPanel address_JPanel2 = new JPanel();
		courseEdit_instructorLabel2 = new JLabel("Instructor");
		courseEdit_instructorText2 = new JTextField(30);
		address_JPanel2.add(courseEdit_instructorLabel2);
		address_JPanel2.add(courseEdit_instructorText2);

		JPanel city_JPanel2 = new JPanel();
		courseEdit_departmentLabel2 = new JLabel("Department");
		courseEdit_departmentText2 = new JTextField(15);
		city_JPanel2.add(courseEdit_departmentLabel2);
		city_JPanel2.add(courseEdit_departmentText2);

		///////

		JPanel oldEditData = new JPanel();
		oldEditData.setLayout(new GridLayout(7, 1));

		oldEditData.add(label_JPanel);
		oldEditData.add(ID_JPanel);
		oldEditData.add(firstName_JPanel);
		oldEditData.add(lastName_JPanel);
		oldEditData.add(address_JPanel);
		oldEditData.add(city_JPanel);

		JPanel newEditData = new JPanel();
		newEditData.setLayout(new GridLayout(7, 1));

		newEditData.add(label_JPanel2);
		newEditData.add(ID_JPanel2);
		newEditData.add(firstName_JPanel2);
		newEditData.add(lastName_JPanel2);
		newEditData.add(address_JPanel2);
		newEditData.add(city_JPanel2);

		JLabel editMenuLabel = new JLabel("Edit Course");
		JPanel editMenuLabelPanel = new JPanel();
		editMenuLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		editMenuLabelPanel.add(editMenuLabel);

		setLayout(new BorderLayout());

		add(oldEditData, BorderLayout.WEST);
		add(newEditData, BorderLayout.EAST);
		add(editMenuLabelPanel, BorderLayout.NORTH);

		JLabel imageLabel = new JLabel();
		ImageIcon arrows = new ImageIcon("rightarrow.png");
		imageLabel.setIcon(arrows);

		add(imageLabel, BorderLayout.CENTER);

	}

}

class CourseSearchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel courseSearch_label;
	private JLabel courseSearch_inputIDLabel;
	private JTextField courseSearch_inputIDText;

	public String getInputCourseIDText() {
		return courseSearch_inputIDText.getText();
	}

	public CourseSearchPanel() throws FileNotFoundException {

		buildCourseSearchPanel();
	}

	public void buildCourseSearchPanel() {
		setLayout(new GridLayout(2, 1));

		JPanel label_JPanel = new JPanel();
		courseSearch_label = new JLabel("Search Course");
		label_JPanel.add(courseSearch_label);

		JPanel enterID_JPanel = new JPanel();
		courseSearch_inputIDLabel = new JLabel("Enter course ID to search");
		courseSearch_inputIDText = new JTextField(15);
		enterID_JPanel.add(courseSearch_inputIDLabel);
		enterID_JPanel.add(courseSearch_inputIDText);

		add(label_JPanel);
		add(enterID_JPanel);
	}

}

class EnrollmentFirstPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel enrollmentSearch_label;
	private JLabel enrollmentSearch_inputIDLabel;
	private JTextField enrollmentSearch_inputIDText;

	public String getInputStudentIDText() {
		return enrollmentSearch_inputIDText.getText();
	}

	public EnrollmentFirstPanel() throws FileNotFoundException {

		buildEnrollmentFirstPanel();
	}

	public void buildEnrollmentFirstPanel() {
		setLayout(new GridLayout(2, 1));

		JPanel label_JPanel = new JPanel();
		enrollmentSearch_label = new JLabel("Select Student");
		label_JPanel.add(enrollmentSearch_label);

		JPanel enterID_JPanel = new JPanel();
		enrollmentSearch_inputIDLabel = new JLabel("Enter student ID to search");
		enrollmentSearch_inputIDText = new JTextField(15);
		enterID_JPanel.add(enrollmentSearch_inputIDLabel);
		enterID_JPanel.add(enrollmentSearch_inputIDText);

		add(label_JPanel);
		add(enterID_JPanel);
	}

}

class EnrollmentSecondPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Student student;
	Course course;
	private JLabel studentAfterAdd_label;
	private JLabel studentAfterAdd_firstNameLabel;
	private JLabel studentAfterAdd_lastNameLabel;
	private JLabel studentAfterAdd_addressLabel;
	private JLabel studentAfterAdd_cityLabel;
	private JLabel studentAfterAdd_stateLabel;
	private JTextField studentAfterAdd_firstNameText;
	private JTextField studentAfterAdd_lastNameText;
	private JTextField studentAfterAdd_addressText;
	private JTextField studentAfterAdd_cityText;
	private JTextField studentAfterAdd_stateText;
	private JLabel studentAfterAdd_IDLabel;
	private JTextField studentAfterAdd_IDText;

	private JLabel createCourseEnrollment_Label;
	private JLabel createCourseEnrollment_courseIDLabel;
	private JLabel createCourseEnrollment_yearLabel;
	private JLabel createCourseEnrollment_semesterLabel;
	private JTextField createCourseEnrollment_courseIDText;
	private JTextField createCourseEnrollment_yearText;
	private JTextField createCourseEnrollment_semesterText;

	public String getStudentID() {
		return this.studentAfterAdd_IDText.getText();
	}

	public String getCourseID() {
		return this.createCourseEnrollment_courseIDText.getText();
	}

	public String getYear() {
		return this.createCourseEnrollment_yearText.getText();
	}

	public String getSemester() {
		return this.createCourseEnrollment_semesterText.getText();
	}

	public EnrollmentSecondPanel(Student temp) {
		this.student = temp;
		buildEnrollmentSecondPanel();
	}

	public void buildEnrollmentSecondPanel() {

		JPanel label_JPanel = new JPanel();
		studentAfterAdd_label = new JLabel("Selected Student");
		label_JPanel.add(studentAfterAdd_label);

		JPanel ID_JPanel = new JPanel();
		studentAfterAdd_IDLabel = new JLabel("Student ID");
		String intToString = String.valueOf(student.getID());
		studentAfterAdd_IDText = new JTextField(intToString);
		studentAfterAdd_IDText.setEditable(false);
		ID_JPanel.add(studentAfterAdd_IDLabel);
		ID_JPanel.add(studentAfterAdd_IDText);

		JPanel firstName_JPanel = new JPanel();
		studentAfterAdd_firstNameLabel = new JLabel("First Name");
		studentAfterAdd_firstNameText = new JTextField(student.getFirstName());
		studentAfterAdd_firstNameText.setEditable(false);
		firstName_JPanel.add(studentAfterAdd_firstNameLabel);
		firstName_JPanel.add(studentAfterAdd_firstNameText);

		JPanel lastName_JPanel = new JPanel();
		studentAfterAdd_lastNameLabel = new JLabel("Last Name");
		studentAfterAdd_lastNameText = new JTextField(student.getLastName());
		studentAfterAdd_lastNameText.setEditable(false);
		lastName_JPanel.add(studentAfterAdd_lastNameLabel);
		lastName_JPanel.add(studentAfterAdd_lastNameText);

		JPanel address_JPanel = new JPanel();
		studentAfterAdd_addressLabel = new JLabel("Address");
		studentAfterAdd_addressText = new JTextField(student.getAddress());
		studentAfterAdd_addressText.setEditable(false);
		address_JPanel.add(studentAfterAdd_addressLabel);
		address_JPanel.add(studentAfterAdd_addressText);

		JPanel city_JPanel = new JPanel();
		studentAfterAdd_cityLabel = new JLabel("City");
		studentAfterAdd_cityText = new JTextField(student.getCity());
		studentAfterAdd_cityText.setEditable(false);
		city_JPanel.add(studentAfterAdd_cityLabel);
		city_JPanel.add(studentAfterAdd_cityText);

		JPanel state_JPanel = new JPanel();
		studentAfterAdd_stateLabel = new JLabel("State");
		studentAfterAdd_stateText = new JTextField(student.getState());
		studentAfterAdd_stateText.setEditable(false);
		state_JPanel.add(studentAfterAdd_stateLabel);
		state_JPanel.add(studentAfterAdd_stateText);

		JPanel studentDisplaySecondPanel = new JPanel();
		studentDisplaySecondPanel.setLayout(new GridLayout(7, 1));

		studentDisplaySecondPanel.add(label_JPanel);
		studentDisplaySecondPanel.add(ID_JPanel);
		studentDisplaySecondPanel.add(firstName_JPanel);
		studentDisplaySecondPanel.add(lastName_JPanel);
		studentDisplaySecondPanel.add(address_JPanel);
		studentDisplaySecondPanel.add(city_JPanel);
		studentDisplaySecondPanel.add(state_JPanel);

		JPanel courseDisplaySecondPanel = new JPanel();
		courseDisplaySecondPanel.setLayout(new GridLayout(4, 1));

		JPanel courseLabel = new JPanel();
		createCourseEnrollment_Label = new JLabel("Create Enrollment");
		courseLabel.add(createCourseEnrollment_Label);

		JPanel courseID_JPanel = new JPanel();
		createCourseEnrollment_courseIDLabel = new JLabel("Course ID");
		createCourseEnrollment_courseIDText = new JTextField(4);
		courseID_JPanel.add(createCourseEnrollment_courseIDLabel);
		courseID_JPanel.add(createCourseEnrollment_courseIDText);

		JPanel courseYear_JPanel = new JPanel();
		createCourseEnrollment_yearLabel = new JLabel("Course Year");
		createCourseEnrollment_yearText = new JTextField(4);
		courseYear_JPanel.add(createCourseEnrollment_yearLabel);
		courseYear_JPanel.add(createCourseEnrollment_yearText);

		JPanel courseSemester_JPanel = new JPanel();
		createCourseEnrollment_semesterLabel = new JLabel("Course Semester");
		createCourseEnrollment_semesterText = new JTextField(8);
		courseSemester_JPanel.add(createCourseEnrollment_semesterLabel);
		courseSemester_JPanel.add(createCourseEnrollment_semesterText);

		courseDisplaySecondPanel.add(courseLabel);
		courseDisplaySecondPanel.add(courseID_JPanel);
		courseDisplaySecondPanel.add(courseYear_JPanel);
		courseDisplaySecondPanel.add(courseSemester_JPanel);

		JLabel createEnrollmentLabel = new JLabel("Enrollment");
		JPanel createEnrollmentPanel = new JPanel();
		createEnrollmentLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
		createEnrollmentPanel.add(createEnrollmentLabel);

		setLayout(new BorderLayout());

		add(studentDisplaySecondPanel, BorderLayout.WEST);
		add(courseDisplaySecondPanel, BorderLayout.EAST);
		add(createEnrollmentPanel, BorderLayout.NORTH);

	}
}

class EnrollmentDisplayPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Enrollment enrollment;
	private JLabel enrollmentDisplay_label;
	private JLabel enrollmentDisplay_EnrollmentIDLabel;
	private JLabel enrollmentDisplay_CourseIDLabel;
	private JLabel enrollmentDisplay_StudentIDLabel;
	private JLabel enrollmentDisplay_YearLabel;
	private JLabel enrollmentDisplay_SemesterLabel;
	private JLabel enrollmentDisplay_GradeLabel;

	private JTextField enrollmentDisplay_EnrollmentIDText;
	private JTextField enrollmentDisplay_CourseIDText;
	private JTextField enrollmentDisplay_StudentIDText;
	private JTextField enrollmentDisplay_YearText;
	private JTextField enrollmentDisplay_SemesterText;
	private JTextField enrollmentDisplay_GradeText;

	public EnrollmentDisplayPanel(Enrollment enrollment) {

		this.enrollment = enrollment;
		buildEnrollmentDisplayPanel();
	}

	public void buildEnrollmentDisplayPanel() {
		setLayout(new GridLayout(7, 1));

		JPanel label_JPanel = new JPanel();
		enrollmentDisplay_label = new JLabel("Display Enrollment");
		label_JPanel.add(enrollmentDisplay_label);

		JPanel enrollmentID_JPanel = new JPanel();
		enrollmentDisplay_EnrollmentIDLabel = new JLabel("Enrollment ID");
		String intToString = String.valueOf(enrollment.getEID());
		enrollmentDisplay_EnrollmentIDText = new JTextField(intToString);
		enrollmentDisplay_EnrollmentIDText.setEditable(false);
		enrollmentID_JPanel.add(enrollmentDisplay_EnrollmentIDLabel);
		enrollmentID_JPanel.add(enrollmentDisplay_EnrollmentIDText);

		JPanel courseID_JPanel = new JPanel();
		enrollmentDisplay_CourseIDLabel = new JLabel("Course ID");
		String intToString2 = String.valueOf(enrollment.getcNum());
		enrollmentDisplay_CourseIDText = new JTextField(intToString2);
		enrollmentDisplay_CourseIDText.setEditable(false);
		courseID_JPanel.add(enrollmentDisplay_CourseIDLabel);
		courseID_JPanel.add(enrollmentDisplay_CourseIDText);

		JPanel studentID_JPanel = new JPanel();
		enrollmentDisplay_StudentIDLabel = new JLabel("Student ID");
		String intToString3 = String.valueOf(enrollment.getStudentID());
		enrollmentDisplay_StudentIDText = new JTextField(intToString3);
		enrollmentDisplay_StudentIDText.setEditable(false);
		studentID_JPanel.add(enrollmentDisplay_StudentIDLabel);
		studentID_JPanel.add(enrollmentDisplay_StudentIDText);

		JPanel year_JPanel = new JPanel();
		enrollmentDisplay_YearLabel = new JLabel("Year");
		enrollmentDisplay_YearText = new JTextField(enrollment.getYear());
		enrollmentDisplay_YearText.setEditable(false);
		year_JPanel.add(enrollmentDisplay_YearLabel);
		year_JPanel.add(enrollmentDisplay_YearText);

		JPanel semester_JPanel = new JPanel();
		enrollmentDisplay_SemesterLabel = new JLabel("Semester");
		enrollmentDisplay_SemesterText = new JTextField(enrollment.getSemester());
		enrollmentDisplay_SemesterText.setEditable(false);
		semester_JPanel.add(enrollmentDisplay_SemesterLabel);
		semester_JPanel.add(enrollmentDisplay_SemesterText);

		JPanel grade_JPanel = new JPanel();
		enrollmentDisplay_GradeLabel = new JLabel("Grade");
		enrollmentDisplay_GradeText = new JTextField(enrollment.getGrade());
		enrollmentDisplay_GradeText.setEditable(false);
		grade_JPanel.add(enrollmentDisplay_GradeLabel);
		grade_JPanel.add(enrollmentDisplay_GradeText);

		add(label_JPanel);
		add(enrollmentID_JPanel);
		add(courseID_JPanel);
		add(studentID_JPanel);
		add(year_JPanel);
		add(semester_JPanel);
		add(grade_JPanel);

	}

}

class EnrollmentEditPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Enrollment enrollment;
	private JLabel enrollmentDisplay_label;
	private JLabel enrollmentDisplay_EnrollmentIDLabel;
	private JLabel enrollmentDisplay_CourseIDLabel;
	private JLabel enrollmentDisplay_StudentIDLabel;
	private JLabel enrollmentDisplay_YearLabel;
	private JLabel enrollmentDisplay_SemesterLabel;
	private JLabel enrollmentDisplay_GradeLabel;

	private JTextField enrollmentDisplay_EnrollmentIDText;
	private JTextField enrollmentDisplay_CourseIDText;
	private JTextField enrollmentDisplay_StudentIDText;
	private JTextField enrollmentDisplay_YearText;
	private JTextField enrollmentDisplay_SemesterText;
	private JTextField enrollmentDisplay_GradeText;

	// 2 stands for user input version
	private JLabel enrollmentDisplay_label2;
	private JLabel enrollmentDisplay_EnrollmentIDLabel2;
	private JLabel enrollmentDisplay_CourseIDLabel2;
	private JLabel enrollmentDisplay_StudentIDLabel2;
	private JLabel enrollmentDisplay_YearLabel2;
	private JLabel enrollmentDisplay_SemesterLabel2;
	private JLabel enrollmentDisplay_GradeLabel2;

	private JTextField enrollmentDisplay_EnrollmentIDText2;
	private JTextField enrollmentDisplay_CourseIDText2;
	private JTextField enrollmentDisplay_StudentIDText2;
	private JTextField enrollmentDisplay_YearText2;
	private JTextField enrollmentDisplay_SemesterText2;
	private JTextField enrollmentDisplay_GradeText2;

	//EnrollmentFile enrollmentRecord; // check if this is needed or not

	public String getEnrollmentDisplay_EnrollmentIDText2() {
		return enrollmentDisplay_EnrollmentIDText2.getText();
	}

	public String getEnrollmentDisplay_CourseIDText2() {
		return enrollmentDisplay_CourseIDText2.getText();
	}

	public String getEnrollmentDisplay_StudentIDText2() {
		return enrollmentDisplay_StudentIDText2.getText();
	}

	public String getEnrollmentDisplay_YearText2() {
		return enrollmentDisplay_YearText2.getText();
	}

	public String getEnrollmentDisplay_SemesterText2() {
		return enrollmentDisplay_SemesterText2.getText();
	}

	public String getEnrollmentDisplay_GradeText2() {
		return enrollmentDisplay_GradeText2.getText();
	}

	public EnrollmentEditPanel(Enrollment temp) throws FileNotFoundException {
		this.enrollment = temp;
		buildCourseEditPanel(enrollment);
	}

	public void buildCourseEditPanel(Enrollment enrollment) {

		JPanel label_JPanel = new JPanel();
		enrollmentDisplay_label = new JLabel("[Old Enrollment Data]");
		label_JPanel.add(enrollmentDisplay_label);

		JPanel EID_JPanel = new JPanel();
		enrollmentDisplay_EnrollmentIDLabel = new JLabel("EID");
		String intToString = String.valueOf(enrollment.getEID());
		enrollmentDisplay_EnrollmentIDText = new JTextField(intToString);
		enrollmentDisplay_EnrollmentIDText.setEditable(false);
		EID_JPanel.add(enrollmentDisplay_EnrollmentIDLabel);
		EID_JPanel.add(enrollmentDisplay_EnrollmentIDText);

		JPanel CID_JPanel = new JPanel();
		enrollmentDisplay_CourseIDLabel = new JLabel("Course ID");
		String intToString2 = String.valueOf(enrollment.getcNum());
		enrollmentDisplay_CourseIDText = new JTextField(intToString2);
		enrollmentDisplay_CourseIDText.setEditable(false);
		CID_JPanel.add(enrollmentDisplay_CourseIDLabel);
		CID_JPanel.add(enrollmentDisplay_CourseIDText);

		JPanel SID_JPanel = new JPanel();
		enrollmentDisplay_StudentIDLabel = new JLabel("Student ID");
		String intToString3 = String.valueOf(enrollment.getStudentID());
		enrollmentDisplay_StudentIDText = new JTextField(intToString3);
		enrollmentDisplay_StudentIDText.setEditable(false);
		SID_JPanel.add(enrollmentDisplay_StudentIDLabel);
		SID_JPanel.add(enrollmentDisplay_StudentIDText);

		JPanel Year_JPanel = new JPanel();
		enrollmentDisplay_YearLabel = new JLabel("Year");
		enrollmentDisplay_YearText = new JTextField(enrollment.getYear());
		enrollmentDisplay_YearText.setEditable(false);
		Year_JPanel.add(enrollmentDisplay_YearLabel);
		Year_JPanel.add(enrollmentDisplay_YearText);

		JPanel Semester_JPanel = new JPanel();
		enrollmentDisplay_SemesterLabel = new JLabel("Semester");
		enrollmentDisplay_SemesterText = new JTextField(enrollment.getSemester());
		enrollmentDisplay_SemesterText.setEditable(false);
		Semester_JPanel.add(enrollmentDisplay_SemesterLabel);
		Semester_JPanel.add(enrollmentDisplay_SemesterText);

		JPanel Grade_JPanel = new JPanel();
		enrollmentDisplay_GradeLabel = new JLabel("Grade");
		enrollmentDisplay_GradeText = new JTextField(enrollment.getGrade());
		enrollmentDisplay_GradeText.setEditable(false);
		Grade_JPanel.add(enrollmentDisplay_GradeLabel);
		Grade_JPanel.add(enrollmentDisplay_GradeText);

		///// DATA 2 input
		JPanel label_JPanel2 = new JPanel();
		enrollmentDisplay_label2 = new JLabel("[New Student Data]");
		label_JPanel2.add(enrollmentDisplay_label2);

		JPanel EID_JPanel2 = new JPanel();
		enrollmentDisplay_EnrollmentIDLabel2 = new JLabel("EID");
		String intToString4 = String.valueOf(enrollment.getEID());
		enrollmentDisplay_EnrollmentIDText2 = new JTextField(intToString4);
		enrollmentDisplay_EnrollmentIDText2.setEditable(false);
		EID_JPanel2.add(enrollmentDisplay_EnrollmentIDLabel2);
		EID_JPanel2.add(enrollmentDisplay_EnrollmentIDText2);

		JPanel CID_JPanel2 = new JPanel();
		enrollmentDisplay_CourseIDLabel2 = new JLabel("Course ID");
		enrollmentDisplay_CourseIDText2 = new JTextField(4);
		CID_JPanel2.add(enrollmentDisplay_CourseIDLabel2);
		CID_JPanel2.add(enrollmentDisplay_CourseIDText2);

		JPanel SID_JPanel2 = new JPanel();
		enrollmentDisplay_StudentIDLabel2 = new JLabel("Student ID");
		enrollmentDisplay_StudentIDText2 = new JTextField(4);
		SID_JPanel2.add(enrollmentDisplay_StudentIDLabel2);
		SID_JPanel2.add(enrollmentDisplay_StudentIDText2);

		JPanel Year_JPanel2 = new JPanel();
		enrollmentDisplay_YearLabel2 = new JLabel("Year");
		enrollmentDisplay_YearText2 = new JTextField(4);
		Year_JPanel2.add(enrollmentDisplay_YearLabel2);
		Year_JPanel2.add(enrollmentDisplay_YearText2);

		JPanel Semester_JPanel2 = new JPanel();
		enrollmentDisplay_SemesterLabel2 = new JLabel("Semester");
		enrollmentDisplay_SemesterText2 = new JTextField(9);
		Semester_JPanel2.add(enrollmentDisplay_SemesterLabel2);
		Semester_JPanel2.add(enrollmentDisplay_SemesterText2);

		JPanel Grade_JPanel2 = new JPanel();
		enrollmentDisplay_GradeLabel2 = new JLabel("Grade");
		enrollmentDisplay_GradeText2 = new JTextField(enrollment.getGrade());
		enrollmentDisplay_GradeText2.setEditable(false);
		Grade_JPanel2.add(enrollmentDisplay_GradeLabel2);
		Grade_JPanel2.add(enrollmentDisplay_GradeText2);

		///////

		JPanel oldEditData = new JPanel();
		oldEditData.setLayout(new GridLayout(7, 1));

		oldEditData.add(label_JPanel);
		oldEditData.add(EID_JPanel);
		oldEditData.add(CID_JPanel);
		oldEditData.add(SID_JPanel);
		oldEditData.add(Year_JPanel);
		oldEditData.add(Semester_JPanel);
		oldEditData.add(Grade_JPanel);

		JPanel newEditData = new JPanel();
		newEditData.setLayout(new GridLayout(7, 1));

		newEditData.add(label_JPanel2);
		newEditData.add(EID_JPanel2);
		newEditData.add(CID_JPanel2);
		newEditData.add(SID_JPanel2);
		newEditData.add(Year_JPanel2);
		newEditData.add(Semester_JPanel2);
		newEditData.add(Grade_JPanel2);

		JLabel editMenuLabel = new JLabel("Edit Enrollment");
		JPanel editMenuLabelPanel = new JPanel();
		editMenuLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		editMenuLabelPanel.add(editMenuLabel);

		setLayout(new BorderLayout());

		add(oldEditData, BorderLayout.WEST);
		add(newEditData, BorderLayout.EAST);
		add(editMenuLabelPanel, BorderLayout.NORTH);

		JLabel imageLabel = new JLabel();
		ImageIcon arrows = new ImageIcon("rightarrow.png");
		imageLabel.setIcon(arrows);

		add(imageLabel, BorderLayout.CENTER);

	}

}

class EnrollmentSearchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel enrollmentSearch_label;
	private JLabel enrollmentSearch_inputIDLabel;
	private JTextField enrollmentSearch_inputIDText;

	public String getInputEnrollmentIDText() {
		return enrollmentSearch_inputIDText.getText();
	}

	public EnrollmentSearchPanel() throws FileNotFoundException {

		buildEnrollmentSearchPanel();
	}

	public void buildEnrollmentSearchPanel() {
		setLayout(new GridLayout(2, 1));

		JPanel label_JPanel = new JPanel();
		enrollmentSearch_label = new JLabel("Search Enrollment");
		label_JPanel.add(enrollmentSearch_label);

		JPanel enterID_JPanel = new JPanel();
		enrollmentSearch_inputIDLabel = new JLabel("Enter enrollment ID to search");
		enrollmentSearch_inputIDText = new JTextField(15);
		enterID_JPanel.add(enrollmentSearch_inputIDLabel);
		enterID_JPanel.add(enrollmentSearch_inputIDText);

		add(label_JPanel);
		add(enterID_JPanel);
	}

}

class GradeAddPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel gradeIntro_Label;
	private JLabel gradeIntro_SIDLabel;
	private JLabel gradeIntro_YearLabel;
	private JLabel gradeIntro_SemesterLabel;

	private JTextField gradeIntro_SIDText;
	private JTextField gradeIntro_YearText;
	private JTextField gradeIntro_SemesterText;

	public String getGradeIntro_SIDText() {
		return this.gradeIntro_SIDText.getText();
	}

	public String getIntro_YearText() {
		return this.gradeIntro_YearText.getText();
	}

	public String getIntro_SemesterText() {
		return this.gradeIntro_SemesterText.getText();
	}

	public GradeAddPanel() throws FileNotFoundException {
		buildGradePanel();
	}

	public void buildGradePanel() {
		setLayout(new GridLayout(4, 1));

		JPanel label_JPanel = new JPanel();
		gradeIntro_Label = new JLabel("Select Student and Term");
		label_JPanel.add(gradeIntro_Label);

		JPanel SID_JPanel = new JPanel();
		gradeIntro_SIDLabel = new JLabel("Student ID");
		gradeIntro_SIDText = new JTextField(4);
		SID_JPanel.add(gradeIntro_SIDLabel);
		SID_JPanel.add(gradeIntro_SIDText);

		JPanel Year_JPanel = new JPanel();
		gradeIntro_YearLabel = new JLabel("Year");
		gradeIntro_YearText = new JTextField(4);
		Year_JPanel.add(gradeIntro_YearLabel);
		Year_JPanel.add(gradeIntro_YearText);

		JPanel Semester_JPanel = new JPanel();
		gradeIntro_SemesterLabel = new JLabel("Semester");
		gradeIntro_SemesterText = new JTextField(9);
		Semester_JPanel.add(gradeIntro_SemesterLabel);
		Semester_JPanel.add(gradeIntro_SemesterText);

		add(label_JPanel);
		add(SID_JPanel);
		add(Year_JPanel);
		add(Semester_JPanel);
	}

}

class PostGradePanel extends JPanel {
	/**
	 * 
	 */
	Enrollment enrollment;
	EnrollmentFile enrollmentRecord;
	int readValue; // reading enrollment file for each SID
	int studentID; // SID Value entered by user
	private static final long serialVersionUID = 1L;
	private JTextField enrollmentSearch_inputIDText;
	private JLabel postPanel_GradeLabel;
	private JTextArea postPanel_GradeTextArea;
	private JLabel postPanel_EIDLabel;
	private JTextField postPanel_EIDText;
	private JLabel postPanel_NewGradeLabel;
	private JTextField postPanel_NewGradeText;

	public String getPostPanel_EIDText() {
		return this.postPanel_EIDText.getText();
	}

	public String getPostPanel_NewGradeText() {
		return this.postPanel_NewGradeText.getText();
	}

	public String getInputStudentIDText() {
		return enrollmentSearch_inputIDText.getText();
	}

	public PostGradePanel() throws FileNotFoundException {

		buildPostGradePanel();
	}

	public void apphendTextArea(String string) {
		postPanel_GradeTextArea.append(string);
	}

	public void setRows(int num) {
		this.postPanel_GradeTextArea.setRows(num);
	}

	public void buildPostGradePanel() {
		setLayout(new BorderLayout());

		JPanel label_JPanel = new JPanel();
		postPanel_GradeLabel = new JLabel("Enrolled Classes");
		label_JPanel.add(postPanel_GradeLabel);

		JPanel textArea_JPanel = new JPanel();
		postPanel_GradeTextArea = new JTextArea(
				"EID    Course    SID         Year         Semester" + "        Grade \n", 10, 10);
		postPanel_GradeTextArea.setEditable(false);
		textArea_JPanel.add(postPanel_GradeTextArea);

		JPanel course_JPanel = new JPanel();
		postPanel_EIDLabel = new JLabel("Enter EID #");
		postPanel_EIDText = new JTextField(9);
		course_JPanel.add(postPanel_EIDLabel);
		course_JPanel.add(postPanel_EIDText);

		JPanel newGrade_JPanel = new JPanel();
		postPanel_NewGradeLabel = new JLabel("Enter New Grade");
		postPanel_NewGradeText = new JTextField(9);
		newGrade_JPanel.add(postPanel_NewGradeLabel);
		newGrade_JPanel.add(postPanel_NewGradeText);

		JPanel inputPanel = new JPanel();
		inputPanel.add(course_JPanel);
		inputPanel.add(newGrade_JPanel);

		add(label_JPanel, BorderLayout.NORTH);
		add(textArea_JPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);

	}

}

class ReportPrintPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel mainPanel_ReportPrint_Label;
	private JLabel mainPanel_ReportPrint_YearLabel;
	private JTextField mainPanel_ReportPrint_YearText;
	private JLabel mainPanel_ReportPrint_CourseLabel;
	private JTextField mainPanel_ReportPrint_CourseText;
	private JTextArea mainPanel_Report_TextArea;

	public ReportPrintPanel() {
		buildReportPrintPanel();
	}

	public void apphendTextArea(String string) {
		mainPanel_Report_TextArea.append(string);
	}

	public void buildReportPrintPanel() {
		setLayout(new BorderLayout());

		JPanel label_JPanel = new JPanel();
		mainPanel_ReportPrint_Label = new JLabel("Report Print");
		label_JPanel.add(mainPanel_ReportPrint_Label);

		String[] headers = { "EID", "Course #", "SID", "Year", "Semester", "Grade" };
		JPanel textArea_JPanel = new JPanel();

		mainPanel_Report_TextArea = new JTextArea(
				String.format("%-10s", headers[0]) + String.format("%-15s", headers[1])
						+ String.format("%-20s", headers[2]) + String.format("%-20s", headers[3])
						+ String.format("%-22s", headers[4]) + String.format("%s", headers[5]) + "\n",
				10, 10);
		mainPanel_Report_TextArea.append("----------------------------------------------------------------------------"
				+ "------------------------\n");
		mainPanel_Report_TextArea.setLineWrap(false);
		mainPanel_Report_TextArea.setEditable(false);
		textArea_JPanel.add(mainPanel_Report_TextArea);

		JPanel year_JPanel = new JPanel();
		mainPanel_ReportPrint_YearLabel = new JLabel("Enter Year");
		mainPanel_ReportPrint_YearText = new JTextField(9);
		year_JPanel.add(mainPanel_ReportPrint_YearLabel);
		year_JPanel.add(mainPanel_ReportPrint_YearText);

		JPanel course_JPanel = new JPanel();
		mainPanel_ReportPrint_CourseLabel = new JLabel("Enter Course Number");
		mainPanel_ReportPrint_CourseText = new JTextField(9);
		course_JPanel.add(mainPanel_ReportPrint_CourseLabel);
		course_JPanel.add(mainPanel_ReportPrint_CourseText);

		JPanel inputPanel = new JPanel();
		inputPanel.add(year_JPanel);
		inputPanel.add(course_JPanel);

		add(label_JPanel, BorderLayout.NORTH);
		add(textArea_JPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
	}

	public String getMainPanel_ReportPrint_YearText() {
		return this.mainPanel_ReportPrint_YearText.getText();
	}

	public String getMainPanel_ReportPrint_CourseText() {
		return this.mainPanel_ReportPrint_CourseText.getText();
	}
}

public class StudentManagementSystem extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 600;

	private JMenuBar menuBar;
	private JMenu addMenu;
	private JMenuItem studentAdd;
	private JMenuItem courseAdd;
	private JMenuItem enrollmentAdd;

	private JMenu searchMenu;
	private JMenuItem studentSearch;
	private JMenuItem courseSearch;
	private JMenuItem enrollmentSearch;

	private JMenu gradeMenu;
	private JMenuItem gradeAdd;

	private JMenu reportMenu;
	private JMenuItem reportPrint;
	// Student Panel Classes
	private StudentAddPanel studentAddPanel;
	private StudentSearchPanel studentSearchPanel;
	private StudentDisplayPanel studentDisplayPanel;
	private StudentEditPanel studentEditPanel;
	// Course Panel Classes
	private CourseAddPanel courseAddPanel;
	private CourseDisplayPanel courseDisplayPanel;
	private CourseEditPanel courseEditPanel;
	private CourseSearchPanel courseSearchPanel;
	// Enrollment Panel Classes
	private EnrollmentFirstPanel enrollmentFirstPanel;
	private EnrollmentSecondPanel enrollmentSecondPanel;
	private EnrollmentDisplayPanel enrollmentDisplayPanel;
	private EnrollmentEditPanel enrollmentEditPanel;
	private EnrollmentSearchPanel enrollmentSearchPanel;
	// Grade Panel Classes
	private GradeAddPanel gradeAddPanel;
	private PostGradePanel postGradePanel;
	// Report Panel Classes
	private ReportPrintPanel reportPrintPanel;

	// Student Buttons
	private JButton createButton;
	private JButton cancelButton_AddPanel;
	private JButton okButton_DisplayPanel;
	private JButton editButton_DisplayPanel;
	private JButton cancelButton_SearchPanel;
	private JButton searchButton;
	private JButton saveButton_EditPanel;
	private JButton cancelButton_EditPanel;
	// Student Panels
	private JPanel mainPanel;
	private JPanel displayPanel;
	private JPanel searchPanel;
	private JPanel editPanel;
	private JPanel buttonPanel; // to edit display
	// Course Buttons
	private JButton createButton_Course;
	private JButton cancelButton_AddPanel_Course;
	private JButton okButton_DisplayPanel_Course;
	private JButton editButton_DisplayPanel_Course;
	private JButton saveButton_EditPanel_Course;
	private JButton cancelButton_EditPanel_Course;
	private JButton searchButton_Course;
	private JButton cancelButton_SearchPanel_Course;
	// Course Panels
	private JPanel mainPanel_Course;
	private JPanel displayPanel_Course;
	private JPanel searchPanel_Course;
	private JPanel editPanel_Course;
	private JPanel buttonPanel_Course;
	// Enrollment Panels
	private JPanel firstPanel_Enrollment; // gets student ID
	private JPanel mainPanel_Enrollment;
	// private JPanel buttonPanel_FirstPanel_Enrollment;
	private JPanel buttonPanel_SecondPanel_Enrollment;
	private JPanel displayPanel_Enrollment;
	private JPanel buttonPanel_DisplayPanel_Enrollment;
	private JPanel editPanel_Enrollment;
	private JPanel searchPanel_Enrollment;
	// Enrollment Buttons
	private JButton searchButton_FirstPanel_Enrollment;
	private JButton cancelButton_FirstPanel_Enrollment;
	private JButton createButton_SecondPanel_Enrollment;
	private JButton cancelButton_SecondPanel_Enrollment;
	private JButton okButton_DisplayPanel_Enrollment;
	private JButton editButton_DisplayPanel_Enrollment;
	private JButton saveButton_EditPanel_Enrollment;
	private JButton cancelButton_EditPanel_Enrollment;
	private JButton searchButton_Enrollment;
	private JButton cancelButton_SearchPanel_Enrollment;

	// Grade Panels
	private JPanel introPanel_Grade;
	private JPanel postPanel_Grade;
	private JPanel buttonPanel_PostPanel_Grade;
	// Grade Buttons
	private JButton searchButton_IntroPanel_Grade;
	private JButton cancelButton_IntroPanel_Grade;
	private JButton postButton_PostPanel_Grade;
	private JButton cancelButton_PostPanel_Grade;
	// Report Panels
	private JPanel mainPanel_Report;
	// Report Buttons
	private JButton printButton_MainPanel_Report;
	private JButton cancelButton_MainPanel_Report;

	StudentFile studentRecord;
	CourseFile courseRecord;
	EnrollmentFile enrollmentRecord;

	public StudentManagementSystem() {
		setTitle("Student Management System - (use cancel button before new menu selection)");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildMenuBar();
		setVisible(true);

	}

	private void buildMenuBar() {
		menuBar = new JMenuBar();

		buildAddMenu();
		buildSearchMenu();
		buildGradeMenu();
		buildReportsMenu();

		menuBar.add(addMenu);
		menuBar.add(searchMenu);
		menuBar.add(gradeMenu);
		menuBar.add(reportMenu);
		setJMenuBar(menuBar);
	}

	private void buildAddMenu() {
		addMenu = new JMenu("Add");
		studentAdd = new JMenuItem("Student");
		courseAdd = new JMenuItem("Course");
		enrollmentAdd = new JMenuItem("Enrollment");

		addMenu.add(studentAdd);
		addMenu.add(courseAdd);
		addMenu.add(enrollmentAdd);

		studentAdd.addActionListener(new studentAddListener());
		courseAdd.addActionListener(new CourseAddListener());
		enrollmentAdd.addActionListener(new EnrollmentAddListener());
	}

	private void buildSearchMenu() {
		searchMenu = new JMenu("Search");
		studentSearch = new JMenuItem("Student");
		courseSearch = new JMenuItem("Course");
		enrollmentSearch = new JMenuItem("Enrollment");
		// add action listener
		searchMenu.add(studentSearch);
		searchMenu.add(courseSearch);
		searchMenu.add(enrollmentSearch);

		studentSearch.addActionListener(new studentSearchListener());
		courseSearch.addActionListener(new CourseSearchListener());
		enrollmentSearch.addActionListener(new EnrollmentSearchListener());
	}

	private void buildGradeMenu() {
		gradeMenu = new JMenu("Grades");
		gradeAdd = new JMenuItem("Add");

		gradeMenu.add(gradeAdd);

		gradeAdd.addActionListener(new gradeAddListener());
	}

	private void buildReportsMenu() {
		reportMenu = new JMenu("Reports");
		reportPrint = new JMenuItem("Print Report");

		reportMenu.add(reportPrint);

		reportPrint.addActionListener(new reportPrintListener());
	}

	public Student createStudent(int numberOfStudentsInFile) {
		String firstName;
		String lastName;
		String address;
		String city;
		String state;

		firstName = studentAddPanel.getFirstName();
		lastName = studentAddPanel.getLastName();
		address = studentAddPanel.getAddress();
		city = studentAddPanel.getCity();
		state = studentAddPanel.getState();

		Student newStudent = new Student(numberOfStudentsInFile, firstName, lastName, address, city, state);
		return newStudent;

	}

	public Course createCourse(int numberOfCoursesInFile) {
		String CID;
		String cName;
		String instructor;
		String department;

		CID = courseAddPanel.getCID();
		cName = courseAddPanel.getcName();
		instructor = courseAddPanel.getInstructor();
		department = courseAddPanel.getDepartment();

		Course newCourse = new Course(numberOfCoursesInFile, CID, cName, instructor, department);
		return newCourse;
	}

	public Enrollment createEnrollment(int numberOfEnrollmentsInFile) {
		int studentID;
		int cNum;
		String year;
		String semester;
		String grade;

		studentID = Integer.parseInt(enrollmentSecondPanel.getStudentID());
		cNum = Integer.parseInt(enrollmentSecondPanel.getCourseID());
		year = enrollmentSecondPanel.getYear().trim();
		semester = enrollmentSecondPanel.getSemester().trim();
		;
		grade = "";

		Enrollment newEnrollment = new Enrollment(numberOfEnrollmentsInFile, studentID, cNum, year, semester, grade);
		return newEnrollment;
	}

	public void buildDisplayPanel(Student temp) {
		studentDisplayPanel = new StudentDisplayPanel(temp);

	}

	public void fillTextArea() throws FileNotFoundException {
		enrollmentRecord = new EnrollmentFile("enrollment.dat");
		studentRecord = new StudentFile("student.dat");
		int studentIDSubMenu = Integer.parseInt(gradeAddPanel.getGradeIntro_SIDText());
		String yearEntered = gradeAddPanel.getIntro_YearText();
		String semesterEntered = gradeAddPanel.getIntro_SemesterText();
		try {
			enrollmentRecord.moveFilePointer(0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int readValueSID;
		String readValueYear, readValueSemester;

		try {
			postGradePanel
					.apphendTextArea("----------------------------------------------------------------------------"
							+ "------------------------\n");
			for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
				Enrollment temp = enrollmentRecord.readEnrollmentFile();
				readValueSID = temp.getStudentID();
				readValueYear = temp.getYear();
				readValueSemester = temp.getSemester();
				readValueSemester.trim();

				if (readValueSID == studentIDSubMenu && yearEntered.contentEquals(readValueYear) && semesterEntered
						.replaceAll("\\s+", "").equalsIgnoreCase(readValueSemester.replaceAll("\\s+", ""))) {
					studentRecord.moveFilePointer(studentIDSubMenu - 1);
					studentRecord.moveFilePointer(studentIDSubMenu - 1);
					String build = (temp.getEID() + "          " + temp.getcNum() + "          " + temp.getStudentID()
							+ "          " + temp.getYear() + "          " + temp.getSemester() + "          "
							+ temp.getGrade());
					postGradePanel.apphendTextArea(build + "\n");
				}
				if (readValueSID != studentIDSubMenu) {
					enrollmentRecord.moveFilePointer(i + 1);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void fillReportTextArea(String year, int cNum) throws FileNotFoundException {
		enrollmentRecord = new EnrollmentFile("enrollment.dat");

		String yearEntered = year;

		int courseNum = cNum;
		try {
			enrollmentRecord.moveFilePointer(0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int readValueSID;
		String readValueYear;

		try {
			for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
				Enrollment temp = enrollmentRecord.readEnrollmentFile();
				readValueSID = temp.getcNum();
				readValueYear = temp.getYear();

				if (readValueSID == courseNum && yearEntered.contentEquals(readValueYear)) {

					String newSemester = temp.getSemester().replaceAll("[\\s|\\u00A0]+", "");
					String newSID = String.valueOf(temp.getStudentID()).replaceAll("[\\s|\\u00A0]+", "");
					String build = (String.format("%-15s", String.valueOf(temp.getEID()))
							+ String.format("%-20s", String.valueOf(temp.getcNum()))
							+ String.format("%-20s", String.valueOf(newSID)));
					// System.out.print(build); when i print out the format is perfect but for some
					// reason its not in the textArea. Tried everything!
					String build2 = (String.format("%-22s", temp.getYear().replaceAll("[\\s|\\u00A0]+", ""))
							+ String.format("%-30s", newSemester.replaceAll("[\\s|\\u00A0]+", ""))
							+ String.format("%-4s", temp.getGrade().replaceAll("[\\s|\\u00A0]+", "")));
					// System.out.print(build2+"\n"); when i print out the format is perfect but for
					// some reason its not in the textArea. Tried everything!

					reportPrintPanel.apphendTextArea(build + build2 + "\n");
				}
				if (readValueSID != courseNum) {
					enrollmentRecord.moveFilePointer(i + 1);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// Student Listeners
	private class studentAddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				studentRecord = new StudentFile("student.dat");

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			mainPanel = new JPanel();
			try {
				studentAddPanel = new StudentAddPanel(); // Object StudentAddPanel
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			mainPanel.setLayout(new GridLayout(2, 1));
			createButton = new JButton("Create");
			createButton.addActionListener(new CreateButtonListener());
			JPanel buttonPanel = new JPanel();

			buttonPanel.add(createButton);

			cancelButton_AddPanel = new JButton("Cancel");
			cancelButton_AddPanel.addActionListener(new CancelButtonListener());

			buttonPanel.add(cancelButton_AddPanel);
			mainPanel.add(studentAddPanel);
			mainPanel.add(buttonPanel);

			add(mainPanel);
			setVisible(true);
			revalidate();
			repaint();

		}
	}

	private class studentSearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				studentRecord = new StudentFile("student.dat");
				studentSearchPanel = new StudentSearchPanel(); // Object StudentAddPanel
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			searchButton = new JButton("Search");
			searchButton.addActionListener(new SearchButtonListener());
			cancelButton_SearchPanel = new JButton("Cancel");
			cancelButton_SearchPanel.addActionListener(new CancelButton_SearchPanelListener());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(searchButton);
			buttonPanel.add(cancelButton_SearchPanel);
			searchPanel = new JPanel();
			searchPanel.setLayout(new GridLayout(2, 1));
			searchPanel.add(studentSearchPanel);
			searchPanel.add(buttonPanel);
			add(searchPanel);
			setVisible(true);

			revalidate();
			repaint();

		}
	}

	private class CreateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (studentAddPanel.getFirstName().isEmpty() || studentAddPanel.getLastName().isEmpty()
					|| studentAddPanel.getAddress().isEmpty() || studentAddPanel.getCity().isEmpty()
					|| studentAddPanel.getState().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(mainPanel);
				revalidate();
				repaint();
			} else {
				Long tempValue = null;
				try {
					tempValue = new Long(studentRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int numberOfStudentsInFile = tempValue.intValue() + 1; // To offset index 0
				Student temp = createStudent(numberOfStudentsInFile);
				try {
					studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					studentRecord.writeStudentFile(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				remove(mainPanel);
				revalidate();
				repaint();
				displayPanel = new JPanel();
				studentDisplayPanel = new StudentDisplayPanel(temp);
				okButton_DisplayPanel = new JButton("OK");
				okButton_DisplayPanel.addActionListener(new OkButtonListener());

				editButton_DisplayPanel = new JButton("Edit");
				editButton_DisplayPanel.addActionListener(new EditButton_DisplayPanelListener());

				displayPanel.setLayout(new GridLayout(2, 1));
				buttonPanel = new JPanel();
				buttonPanel.add(okButton_DisplayPanel);
				buttonPanel.add(editButton_DisplayPanel);
				displayPanel.add(studentDisplayPanel);
				displayPanel.add(buttonPanel);

				add(displayPanel);
				revalidate();
				repaint();
			}
		}
	}

	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(mainPanel);
			revalidate();
			repaint();
		}
	}

	private class CancelButton_SearchPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(searchPanel);
			revalidate();
			repaint();
		}
	}

	private class OkButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayPanel.setVisible(false);
		}
	}

	private class EditButton_DisplayPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(displayPanel);
			revalidate();
			repaint();

			try {
				studentRecord = new StudentFile("student.dat");
				studentEditPanel = new StudentEditPanel(studentDisplayPanel.student); // pass student object here and
																						// make a constructor
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			saveButton_EditPanel = new JButton("Save");
			saveButton_EditPanel.addActionListener(new SaveButtonListener());
			cancelButton_EditPanel = new JButton("Cancel");
			cancelButton_EditPanel.addActionListener(new CancelButton_EditPanelListener());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(saveButton_EditPanel);
			buttonPanel.add(cancelButton_EditPanel);
			editPanel = new JPanel();
			editPanel.setLayout(new GridLayout(2, 1));
			editPanel.add(studentEditPanel);
			editPanel.add(buttonPanel);
			add(editPanel);
			setVisible(true);
			revalidate();
			repaint();
		}
	}

	private class CancelButton_EditPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(editPanel);
			revalidate();
			repaint();
		}
	}

	private class SaveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (studentEditPanel.getStudentEdit_firstNameText2().isEmpty()
					|| studentEditPanel.getStudentEdit_lastNameText2().isEmpty()
					|| studentEditPanel.getStudentEdit_addressText2().isEmpty()
					|| studentEditPanel.getStudentEdit_cityText2().isEmpty()
					|| studentEditPanel.getStudentEdit_stateText2().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(editPanel);
				revalidate();
				repaint();
			} else {

				int number = studentEditPanel.student.getID() - 1;
				try {
					studentRecord.moveFilePointer(number);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Student temp = null;
				try {
					temp = studentRecord.readStudentFile();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String newFirstName = studentEditPanel.getStudentEdit_firstNameText2();

				String newLastName = studentEditPanel.getStudentEdit_lastNameText2();
				String newAddress = studentEditPanel.getStudentEdit_addressText2();
				String newCity = studentEditPanel.getStudentEdit_cityText2();
				String newState = studentEditPanel.getStudentEdit_stateText2();
				temp.setFirstName(newFirstName);
				temp.setLastName(newLastName);
				temp.setAddress(newAddress);
				temp.setCity(newCity);
				temp.setState(newState);
				try {
					studentRecord.moveFilePointer(number);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					studentRecord.writeStudentFile(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				remove(editPanel);
				revalidate();
				repaint();
				displayPanel = new JPanel();
				studentDisplayPanel = new StudentDisplayPanel(temp);
				okButton_DisplayPanel = new JButton("OK");
				okButton_DisplayPanel.addActionListener(new OkButtonListener());

				editButton_DisplayPanel = new JButton("Edit");
				editButton_DisplayPanel.addActionListener(new EditButton_DisplayPanelListener());

				displayPanel.setLayout(new GridLayout(2, 1));
				buttonPanel = new JPanel();
				buttonPanel.add(okButton_DisplayPanel);
				buttonPanel.add(editButton_DisplayPanel);
				displayPanel.add(studentDisplayPanel);
				displayPanel.add(buttonPanel);

				add(displayPanel);
				revalidate();
				repaint();
			}
		}
	}

	private class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			int SIDValue = 0;
			if (!studentSearchPanel.getInputStudentIDText().isEmpty())
				SIDValue = Integer.parseInt(studentSearchPanel.getInputStudentIDText());
			if (studentSearchPanel.getInputStudentIDText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(searchPanel);
				revalidate();
				repaint();
			} else
				try {
					if (SIDValue > studentRecord.getNumberOfRecords() || SIDValue == 0) {
						try {
							JOptionPane.showMessageDialog(null,
									"Student ID input value of " + SIDValue + " is higher than the actual stored"
											+ " records value of " + studentRecord.getNumberOfRecords()
											+ ". Value also cannot be 0.");
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(searchPanel);
						revalidate();
						repaint();
					}

					else {
						Student temp = null;
						try {
							if (studentRecord.getNumberOfRecords() != 0) {
								int number = -1;
								while (number < 1 || number > studentRecord.getNumberOfRecords()) {
									number = Integer.parseInt(studentSearchPanel.getInputStudentIDText());
								}
								number = number - 1;
								studentRecord.moveFilePointer(number);
								temp = studentRecord.readStudentFile();
								// temp.printStudent();
								studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());

							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// setVisible(false);
						remove(searchPanel);
						revalidate();
						repaint();
						displayPanel = new JPanel();
						studentDisplayPanel = new StudentDisplayPanel(temp);
						okButton_DisplayPanel = new JButton("OK");
						okButton_DisplayPanel.addActionListener(new OkButtonListener());

						editButton_DisplayPanel = new JButton("Edit");
						editButton_DisplayPanel.addActionListener(new EditButton_DisplayPanelListener());

						displayPanel.setLayout(new GridLayout(2, 1));
						buttonPanel = new JPanel();
						buttonPanel.add(okButton_DisplayPanel);
						buttonPanel.add(editButton_DisplayPanel);
						displayPanel.add(studentDisplayPanel);
						displayPanel.add(buttonPanel);

						add(displayPanel);
						revalidate();
						repaint();

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
	}

	// Course Listeners
	private class CourseAddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				courseRecord = new CourseFile("course.dat");

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			mainPanel_Course = new JPanel();
			// mainPanel.setLayout(new GridLayout(2,1));
			try {
				courseAddPanel = new CourseAddPanel(); // Object StudentAddPanel
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			mainPanel_Course.setLayout(new GridLayout(2, 1));
			createButton_Course = new JButton("Create");
			createButton_Course.addActionListener(new CreateButtonListener_Course());
			JPanel buttonPanel = new JPanel();

			buttonPanel.add(createButton_Course);

			cancelButton_AddPanel_Course = new JButton("Cancel");
			cancelButton_AddPanel_Course.addActionListener(new CancelButtonListener_Course());

			buttonPanel.add(cancelButton_AddPanel_Course);
			mainPanel_Course.add(courseAddPanel);
			mainPanel_Course.add(buttonPanel);

			add(mainPanel_Course);
			setVisible(true);
			revalidate();
			repaint();

		}
	}

	private class CancelButtonListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(mainPanel_Course);
			revalidate();
			repaint();
		}
	}

	private class CreateButtonListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (courseAddPanel.getCID().isEmpty() || courseAddPanel.getcName().isEmpty()
					|| courseAddPanel.getInstructor().isEmpty() || courseAddPanel.getDepartment().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(mainPanel_Course);
				revalidate();
				repaint();
			} else {
				Long tempValue = null;
				try {
					tempValue = new Long(courseRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int numberOfCoursesInFile = tempValue.intValue() + 1; // To offset index 0
				Course temp = createCourse(numberOfCoursesInFile);
				try {
					courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					courseRecord.writeCourseFile(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				remove(mainPanel_Course);
				revalidate();
				repaint();

				displayPanel_Course = new JPanel();
				courseDisplayPanel = new CourseDisplayPanel(temp);
				okButton_DisplayPanel_Course = new JButton("OK");
				okButton_DisplayPanel_Course.addActionListener(new OkButtonListener_Course());

				editButton_DisplayPanel_Course = new JButton("Edit");
				editButton_DisplayPanel_Course.addActionListener(new EditButton_DisplayPanelListener_Course());

				displayPanel_Course.setLayout(new GridLayout(2, 1));
				buttonPanel_Course = new JPanel();
				buttonPanel_Course.add(okButton_DisplayPanel_Course);
				buttonPanel_Course.add(editButton_DisplayPanel_Course);
				displayPanel_Course.add(courseDisplayPanel);
				displayPanel_Course.add(buttonPanel_Course);

				add(displayPanel_Course);
				revalidate();
				repaint();
			}
		}
	}

	private class OkButtonListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayPanel_Course.setVisible(false);
		}
	}

	private class EditButton_DisplayPanelListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(displayPanel_Course);
			revalidate();
			repaint();

			try {
				courseRecord = new CourseFile("course.dat");
				courseEditPanel = new CourseEditPanel(courseDisplayPanel.course); // pass student object here and make a
																					// constructor
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			saveButton_EditPanel_Course = new JButton("Save");
			saveButton_EditPanel_Course.addActionListener(new SaveButtonListener_Course());
			cancelButton_EditPanel_Course = new JButton("Cancel");
			cancelButton_EditPanel_Course.addActionListener(new CancelButton_EditPanelListener_Course());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(saveButton_EditPanel_Course);
			buttonPanel.add(cancelButton_EditPanel_Course);
			editPanel_Course = new JPanel();
			editPanel_Course.setLayout(new GridLayout(2, 1));
			editPanel_Course.add(courseEditPanel);
			editPanel_Course.add(buttonPanel);
			add(editPanel_Course);
			setVisible(true);
			revalidate();
			repaint();
		}
	}

	private class CancelButton_EditPanelListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(editPanel_Course);
			revalidate();
			repaint();
		}
	}

	private class SaveButtonListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (courseEditPanel.getCourseEdit_CIDText2().isEmpty()
					|| courseEditPanel.getCourseEdit_cNameText2().isEmpty()
					|| courseEditPanel.getCourseEdit_instructorText2().isEmpty()
					|| courseEditPanel.getCourseEdit_departmentText2().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(editPanel_Course);
				revalidate();
				repaint();
			} else {
				int number = courseEditPanel.course.getCNUM() - 1;
				try {
					courseRecord.moveFilePointer(number);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Course temp = null;
				try {
					temp = courseRecord.readCourseFile();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String newCID = courseEditPanel.getCourseEdit_CIDText2();

				String newcName = courseEditPanel.getCourseEdit_cNameText2();
				String newInstructor = courseEditPanel.getCourseEdit_instructorText2();
				String newDepartment = courseEditPanel.getCourseEdit_departmentText2();
				temp.setCID(newCID);
				temp.setcName(newcName);
				temp.setInstructor(newInstructor);
				temp.setDepartment(newDepartment);

				try {
					courseRecord.moveFilePointer(number);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					courseRecord.writeCourseFile(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				remove(editPanel_Course);
				revalidate();
				repaint();
				displayPanel_Course = new JPanel();
				courseDisplayPanel = new CourseDisplayPanel(temp);
				okButton_DisplayPanel_Course = new JButton("OK");
				okButton_DisplayPanel_Course.addActionListener(new OkButtonListener_Course());

				editButton_DisplayPanel_Course = new JButton("Edit");
				editButton_DisplayPanel_Course.addActionListener(new EditButton_DisplayPanelListener_Course());

				displayPanel_Course.setLayout(new GridLayout(2, 1));
				buttonPanel_Course = new JPanel();
				buttonPanel_Course.add(okButton_DisplayPanel_Course);
				buttonPanel_Course.add(editButton_DisplayPanel_Course);
				displayPanel_Course.add(courseDisplayPanel);
				displayPanel_Course.add(buttonPanel_Course);

				add(displayPanel_Course);
				revalidate();
				repaint();
			}
		}
	}

	private class CourseSearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				courseRecord = new CourseFile("course.dat");
				courseSearchPanel = new CourseSearchPanel(); // Object StudentAddPanel
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			searchButton_Course = new JButton("Search");
			searchButton_Course.addActionListener(new SearchButtonListener_Course());
			cancelButton_SearchPanel_Course = new JButton("Cancel");
			cancelButton_SearchPanel_Course.addActionListener(new CancelButton_SearchPanelListener_Course());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(searchButton_Course);
			buttonPanel.add(cancelButton_SearchPanel_Course);
			searchPanel_Course = new JPanel();
			searchPanel_Course.setLayout(new GridLayout(2, 1));
			searchPanel_Course.add(courseSearchPanel);
			searchPanel_Course.add(buttonPanel);
			add(searchPanel_Course);
			setVisible(true);

			revalidate();
			repaint();
		}
	}

	private class SearchButtonListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int SIDValue = 0;
			if (!courseSearchPanel.getInputCourseIDText().isEmpty())
				SIDValue = Integer.parseInt(courseSearchPanel.getInputCourseIDText());
			if (courseSearchPanel.getInputCourseIDText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(searchPanel_Course);
				revalidate();
				repaint();
			} else
				try {
					if (SIDValue > courseRecord.getNumberOfRecords() || SIDValue == 0) {
						try {
							JOptionPane.showMessageDialog(null,
									"Student ID input value of " + SIDValue + " is higher than the actual stored"
											+ " records value of " + courseRecord.getNumberOfRecords()
											+ ". Value also cannot be 0.");
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(searchPanel_Course);
						revalidate();
						repaint();
					}

					else {
						Course temp = null;
						try {
							if (courseRecord.getNumberOfRecords() != 0) {
								int number = -1;
								while (number < 1 || number > courseRecord.getNumberOfRecords()) {
									number = Integer.parseInt(courseSearchPanel.getInputCourseIDText());
								}
								number = number - 1;
								courseRecord.moveFilePointer(number);
								temp = courseRecord.readCourseFile();
								courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(searchPanel_Course);
						revalidate();
						repaint();
						displayPanel_Course = new JPanel();
						courseDisplayPanel = new CourseDisplayPanel(temp);
						okButton_DisplayPanel_Course = new JButton("OK");
						okButton_DisplayPanel_Course.addActionListener(new OkButtonListener_Course());

						editButton_DisplayPanel_Course = new JButton("Edit");
						editButton_DisplayPanel_Course.addActionListener(new EditButton_DisplayPanelListener_Course());

						displayPanel_Course.setLayout(new GridLayout(2, 1));
						buttonPanel_Course = new JPanel();
						buttonPanel_Course.add(okButton_DisplayPanel_Course);
						buttonPanel_Course.add(editButton_DisplayPanel_Course);
						displayPanel_Course.add(courseDisplayPanel);
						displayPanel_Course.add(buttonPanel_Course);

						add(displayPanel_Course);
						revalidate();
						repaint();

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
	}

	private class CancelButton_SearchPanelListener_Course implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(searchPanel_Course);
			revalidate();
			repaint();
		}
	}

	// Enrollment Listeners
	private class EnrollmentAddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				studentRecord = new StudentFile("student.dat");
				enrollmentRecord = new EnrollmentFile("enrollment.dat");

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			firstPanel_Enrollment = new JPanel();
			try {
				enrollmentFirstPanel = new EnrollmentFirstPanel(); // Object StudentAddPanel
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			firstPanel_Enrollment.setLayout(new GridLayout(2, 1));
			searchButton_FirstPanel_Enrollment = new JButton("Search");
			searchButton_FirstPanel_Enrollment.addActionListener(new SearchButton_FirstPanelListener_Enrollment());
			JPanel buttonPanel = new JPanel();

			buttonPanel.add(searchButton_FirstPanel_Enrollment);

			cancelButton_FirstPanel_Enrollment = new JButton("Cancel");
			cancelButton_FirstPanel_Enrollment.addActionListener(new CancelButton_FirstPanelListener_Enrollment());

			buttonPanel.add(cancelButton_FirstPanel_Enrollment);
			firstPanel_Enrollment.add(enrollmentFirstPanel);
			firstPanel_Enrollment.add(buttonPanel);

			add(firstPanel_Enrollment);
			setVisible(true);
			revalidate();
			repaint();

		}
	}

	private class CancelButton_FirstPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(firstPanel_Enrollment);
			revalidate();
			repaint();
		}
	}

	private class SearchButton_FirstPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int SIDValue = 0;
			if (!enrollmentFirstPanel.getInputStudentIDText().isEmpty())
				SIDValue = Integer.parseInt(enrollmentFirstPanel.getInputStudentIDText());
			if (enrollmentFirstPanel.getInputStudentIDText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(firstPanel_Enrollment);
				revalidate();
				repaint();
			} else
				try {
					if (SIDValue > studentRecord.getNumberOfRecords() || SIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Student ID input value of " + SIDValue + " is higher than the actual stored"
										+ " records value of " + studentRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(firstPanel_Enrollment);
						revalidate();
						repaint();
					}

					else {
						Student temp = null;
						try {
							if (studentRecord.getNumberOfRecords() != 0) {
								int number = -1;
								while (number < 1 || number > studentRecord.getNumberOfRecords()) {
									number = Integer.parseInt(enrollmentFirstPanel.getInputStudentIDText());
								}
								number = number - 1;
								studentRecord.moveFilePointer(number);
								temp = studentRecord.readStudentFile();
								studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());

							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(firstPanel_Enrollment);
						revalidate();
						repaint();
						mainPanel_Enrollment = new JPanel();
						enrollmentSecondPanel = new EnrollmentSecondPanel(temp);
						createButton_SecondPanel_Enrollment = new JButton("Create");
						createButton_SecondPanel_Enrollment
								.addActionListener(new CreateButton_SecondPanelListener_Enrollment());

						cancelButton_SecondPanel_Enrollment = new JButton("Cancel");
						cancelButton_SecondPanel_Enrollment
								.addActionListener(new CancelButton_SecondPanelListener_Enrollment());

						mainPanel_Enrollment.setLayout(new GridLayout(2, 1));
						buttonPanel_SecondPanel_Enrollment = new JPanel();
						buttonPanel_SecondPanel_Enrollment.add(createButton_SecondPanel_Enrollment);
						buttonPanel_SecondPanel_Enrollment.add(cancelButton_SecondPanel_Enrollment);
						mainPanel_Enrollment.add(enrollmentSecondPanel);
						mainPanel_Enrollment.add(buttonPanel_SecondPanel_Enrollment);

						add(mainPanel_Enrollment);
						revalidate();
						repaint();
					}
				} catch (HeadlessException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	private class CancelButton_SecondPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(mainPanel_Enrollment);
			revalidate();
			repaint();
		}
	}

	private class CreateButton_SecondPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (enrollmentSecondPanel.getCourseID().isEmpty() || enrollmentSecondPanel.getYear().isEmpty()
					|| enrollmentSecondPanel.getSemester().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(mainPanel_Enrollment);
				revalidate();
				repaint();
			} else {
				Long tempValue = null;
				try {
					tempValue = new Long(enrollmentRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int numberOfEnrollmentsInFile = tempValue.intValue() + 1; // To offset index 0
				Enrollment temp = createEnrollment(numberOfEnrollmentsInFile);
				temp.setGrade("N"); // Sets grade to no value instead of Blank
				try {
					enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					enrollmentRecord.writeEnrollmentFile(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				remove(mainPanel_Enrollment);
				revalidate();
				repaint();

				displayPanel_Enrollment = new JPanel();
				enrollmentDisplayPanel = new EnrollmentDisplayPanel(temp);
				okButton_DisplayPanel_Enrollment = new JButton("OK");
				okButton_DisplayPanel_Enrollment.addActionListener(new OkButton_DisplayPanelListener_Enrollment());

				editButton_DisplayPanel_Enrollment = new JButton("Edit");
				editButton_DisplayPanel_Enrollment.addActionListener(new EditButton_DisplayPanelListener_Enrollment());

				displayPanel_Enrollment.setLayout(new GridLayout(2, 1));
				buttonPanel_DisplayPanel_Enrollment = new JPanel();
				buttonPanel_DisplayPanel_Enrollment.add(okButton_DisplayPanel_Enrollment);
				buttonPanel_DisplayPanel_Enrollment.add(editButton_DisplayPanel_Enrollment);
				displayPanel_Enrollment.add(enrollmentDisplayPanel);
				displayPanel_Enrollment.add(buttonPanel_DisplayPanel_Enrollment);

				add(displayPanel_Enrollment);
				revalidate();
				repaint();
			}
		}
	}

	private class OkButton_DisplayPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(displayPanel_Enrollment);
			revalidate();
			repaint();
		}
	}

	private class CancelButton_EditPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(editPanel_Enrollment);
			revalidate();
			repaint();
		}
	}

	private class EditButton_DisplayPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(displayPanel_Enrollment);
			revalidate();
			repaint();

			try {
				enrollmentRecord = new EnrollmentFile("enrollment.dat");
				enrollmentEditPanel = new EnrollmentEditPanel(enrollmentDisplayPanel.enrollment);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			saveButton_EditPanel_Enrollment = new JButton("Save");
			saveButton_EditPanel_Enrollment.addActionListener(new SaveButtonListener_Enrollment());
			cancelButton_EditPanel_Enrollment = new JButton("Cancel");
			cancelButton_EditPanel_Enrollment.addActionListener(new CancelButton_EditPanelListener_Enrollment());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(saveButton_EditPanel_Enrollment);
			buttonPanel.add(cancelButton_EditPanel_Enrollment);
			editPanel_Enrollment = new JPanel();
			editPanel_Enrollment.setLayout(new GridLayout(2, 1));
			editPanel_Enrollment.add(enrollmentEditPanel);
			editPanel_Enrollment.add(buttonPanel);
			add(editPanel_Enrollment);
			setVisible(true);
			revalidate();
			repaint();
		}
	}

	private class SaveButtonListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int CIDValue = 0;
			int SIDValue = 0;
			if (!enrollmentEditPanel.getEnrollmentDisplay_CourseIDText2().isEmpty())
				CIDValue = Integer.parseInt(enrollmentEditPanel.getEnrollmentDisplay_CourseIDText2());
			if (!enrollmentEditPanel.getEnrollmentDisplay_StudentIDText2().isEmpty())
				SIDValue = Integer.parseInt(enrollmentEditPanel.getEnrollmentDisplay_StudentIDText2());
			if (enrollmentEditPanel.getEnrollmentDisplay_CourseIDText2().isEmpty()
					|| enrollmentEditPanel.getEnrollmentDisplay_StudentIDText2().isEmpty()
					|| enrollmentEditPanel.getEnrollmentDisplay_YearText2().isEmpty()
					|| enrollmentEditPanel.getEnrollmentDisplay_SemesterText2().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(editPanel_Enrollment);
				revalidate();
				repaint();
			} else
				try {
					courseRecord = new CourseFile("course.dat");
					studentRecord = new StudentFile("student.dat");
					if (CIDValue > courseRecord.getNumberOfRecords() || CIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Course ID input value of " + CIDValue + " is higher than the actual stored"
										+ " records value of " + courseRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(editPanel_Enrollment);
						revalidate();
						repaint();
					} else if (SIDValue > studentRecord.getNumberOfRecords() || SIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Student ID input value of " + SIDValue + " is higher than the actual stored"
										+ " records value of " + studentRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(editPanel_Enrollment);
						revalidate();
						repaint();
					}

					else {
						int number = enrollmentEditPanel.enrollment.getEID() - 1;
						try {
							enrollmentRecord.moveFilePointer(number);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						Enrollment temp = null;
						try {
							temp = enrollmentRecord.readEnrollmentFile();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						String newCID = enrollmentEditPanel.getEnrollmentDisplay_CourseIDText2();
						String newSID = enrollmentEditPanel.getEnrollmentDisplay_StudentIDText2();
						String newYear = enrollmentEditPanel.getEnrollmentDisplay_YearText2();
						String newSemester = enrollmentEditPanel.getEnrollmentDisplay_SemesterText2();
						temp.setcNum(Integer.parseInt(newCID));
						temp.setStudentID(Integer.parseInt(newSID));
						temp.setYear(newYear);
						temp.setSemester(newSemester);
						try {
							enrollmentRecord.moveFilePointer(number);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							enrollmentRecord.writeEnrollmentFile(temp);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(editPanel_Enrollment);
						revalidate();
						repaint();
						displayPanel_Enrollment = new JPanel();
						enrollmentDisplayPanel = new EnrollmentDisplayPanel(temp);
						okButton_DisplayPanel_Enrollment = new JButton("OK");
						okButton_DisplayPanel_Enrollment
								.addActionListener(new OkButton_DisplayPanelListener_Enrollment());

						editButton_DisplayPanel_Enrollment = new JButton("Edit");
						editButton_DisplayPanel_Enrollment
								.addActionListener(new EditButton_DisplayPanelListener_Enrollment());

						displayPanel_Enrollment.setLayout(new GridLayout(2, 1));
						buttonPanel_DisplayPanel_Enrollment = new JPanel();
						buttonPanel_DisplayPanel_Enrollment.add(okButton_DisplayPanel_Enrollment);
						buttonPanel_DisplayPanel_Enrollment.add(editButton_DisplayPanel_Enrollment);
						displayPanel_Enrollment.add(enrollmentDisplayPanel);
						displayPanel_Enrollment.add(buttonPanel_DisplayPanel_Enrollment);

						add(displayPanel_Enrollment);
						revalidate();
						repaint();
					}
				} catch (HeadlessException | NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	private class EnrollmentSearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				enrollmentRecord = new EnrollmentFile("enrollment.dat");
				enrollmentSearchPanel = new EnrollmentSearchPanel();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			searchButton_Enrollment = new JButton("Search");
			searchButton_Enrollment.addActionListener(new SearchButtonListener_Enrollment());
			cancelButton_SearchPanel_Enrollment = new JButton("Cancel");
			cancelButton_SearchPanel_Enrollment.addActionListener(new CancelButton_SearchPanelListener_Enrollment());
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(searchButton_Enrollment);
			buttonPanel.add(cancelButton_SearchPanel_Enrollment);
			searchPanel_Enrollment = new JPanel();
			searchPanel_Enrollment.setLayout(new GridLayout(2, 1));
			searchPanel_Enrollment.add(enrollmentSearchPanel);
			searchPanel_Enrollment.add(buttonPanel);
			add(searchPanel_Enrollment);
			setVisible(true);

			revalidate();
			repaint();
		}
	}

	private class CancelButton_SearchPanelListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(searchPanel_Enrollment);
			revalidate();
			repaint();
		}
	}

	private class SearchButtonListener_Enrollment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int SIDValue = 0;
			if (!enrollmentSearchPanel.getInputEnrollmentIDText().isEmpty())
				SIDValue = Integer.parseInt(enrollmentSearchPanel.getInputEnrollmentIDText());
			if (enrollmentSearchPanel.getInputEnrollmentIDText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(searchPanel_Enrollment);
				revalidate();
				repaint();
			} else
				try {
					if (SIDValue > enrollmentRecord.getNumberOfRecords() || SIDValue == 0) {
						try {
							JOptionPane.showMessageDialog(null,
									"Student ID input value of " + SIDValue + " is higher than the actual stored"
											+ " records value of " + enrollmentRecord.getNumberOfRecords()
											+ ". Value also cannot be 0.");
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(searchPanel_Enrollment);
						revalidate();
						repaint();
					} else {
						Enrollment temp = null;
						try {
							if (enrollmentRecord.getNumberOfRecords() != 0) {
								int number = -1;
								while (number < 1 || number > enrollmentRecord.getNumberOfRecords()) {
									number = Integer.parseInt(enrollmentSearchPanel.getInputEnrollmentIDText());
								}
								number = number - 1;
								enrollmentRecord.moveFilePointer(number);
								temp = enrollmentRecord.readEnrollmentFile();
								enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						remove(searchPanel_Enrollment);
						revalidate();
						repaint();
						displayPanel_Enrollment = new JPanel();
						enrollmentDisplayPanel = new EnrollmentDisplayPanel(temp);
						okButton_DisplayPanel_Enrollment = new JButton("OK");
						okButton_DisplayPanel_Enrollment
								.addActionListener(new OkButton_DisplayPanelListener_Enrollment());

						editButton_DisplayPanel_Enrollment = new JButton("Edit");
						editButton_DisplayPanel_Enrollment
								.addActionListener(new EditButton_DisplayPanelListener_Enrollment());

						displayPanel_Enrollment.setLayout(new GridLayout(2, 1));
						buttonPanel_DisplayPanel_Enrollment = new JPanel();
						buttonPanel_DisplayPanel_Enrollment.add(okButton_DisplayPanel_Enrollment);
						buttonPanel_DisplayPanel_Enrollment.add(editButton_DisplayPanel_Enrollment);
						displayPanel_Enrollment.add(enrollmentDisplayPanel);
						displayPanel_Enrollment.add(buttonPanel_DisplayPanel_Enrollment);

						add(displayPanel_Enrollment);
						revalidate();
						repaint();

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	// Grade Listeners
	private class gradeAddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				enrollmentRecord = new EnrollmentFile("enrollment.dat");

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			introPanel_Grade = new JPanel();
			try {
				gradeAddPanel = new GradeAddPanel();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			introPanel_Grade.setLayout(new GridLayout(2, 1));
			searchButton_IntroPanel_Grade = new JButton("Search");
			searchButton_IntroPanel_Grade.addActionListener(new SearchButton_IntroPanelListener_Grade());
			JPanel buttonPanel = new JPanel();

			buttonPanel.add(searchButton_IntroPanel_Grade);

			cancelButton_IntroPanel_Grade = new JButton("Cancel");
			cancelButton_IntroPanel_Grade.addActionListener(new CancelButton_IntroPanelListener_Grade());

			buttonPanel.add(cancelButton_IntroPanel_Grade);
			introPanel_Grade.add(gradeAddPanel);
			introPanel_Grade.add(buttonPanel);

			add(introPanel_Grade);
			setVisible(true);
			revalidate();
			repaint();

		}
	}

	private class CancelButton_IntroPanelListener_Grade implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(introPanel_Grade);
			revalidate();
			repaint();
		}
	}

	private class SearchButton_IntroPanelListener_Grade implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int SIDValue = 0;
			if (!gradeAddPanel.getGradeIntro_SIDText().isEmpty())
				SIDValue = Integer.parseInt(gradeAddPanel.getGradeIntro_SIDText());
			if (gradeAddPanel.getGradeIntro_SIDText().isEmpty() || gradeAddPanel.getIntro_YearText().isEmpty()
					|| gradeAddPanel.getIntro_SemesterText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(introPanel_Grade);
				revalidate();
				repaint();
			} else
				try {
					studentRecord = new StudentFile("student.dat");
					if (SIDValue > studentRecord.getNumberOfRecords() || SIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Student ID input value of " + SIDValue + " is higher than the actual stored"
										+ " records value of " + studentRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(introPanel_Grade);
						revalidate();
						repaint();
					} else {

						remove(introPanel_Grade);
						revalidate();
						repaint();
						postPanel_Grade = new JPanel();
						try {
							postGradePanel = new PostGradePanel();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						postButton_PostPanel_Grade = new JButton("Post");
						postButton_PostPanel_Grade.addActionListener(new PostButton_PostPanelListener_Grade());

						cancelButton_PostPanel_Grade = new JButton("Cancel");
						cancelButton_PostPanel_Grade.addActionListener(new CancelButton_PostPanelListener_Grade());

						postPanel_Grade.setLayout(new GridLayout(2, 1));
						buttonPanel_PostPanel_Grade = new JPanel();
						buttonPanel_PostPanel_Grade.add(postButton_PostPanel_Grade);
						buttonPanel_PostPanel_Grade.add(cancelButton_PostPanel_Grade);
						postPanel_Grade.add(postGradePanel);
						postPanel_Grade.add(buttonPanel_PostPanel_Grade);

						add(postPanel_Grade);
						revalidate();
						repaint();

						try {
							fillTextArea();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				} catch (HeadlessException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
	}

	private class CancelButton_PostPanelListener_Grade implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(postPanel_Grade);
			revalidate();
			repaint();
		}
	}

	private class PostButton_PostPanelListener_Grade implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int EIDValue = 0;
			if (!postGradePanel.getPostPanel_EIDText().isEmpty())
				EIDValue = Integer.parseInt(postGradePanel.getPostPanel_EIDText());
			if (postGradePanel.getPostPanel_EIDText().isEmpty()
					|| postGradePanel.getPostPanel_NewGradeText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(postPanel_Grade);
				revalidate();
				repaint();
			} else
				try {
					enrollmentRecord = new EnrollmentFile("enrollment.dat");
					if (EIDValue > enrollmentRecord.getNumberOfRecords() || EIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Enrollment ID input value of " + EIDValue + " is higher than the actual stored"
										+ " records value of " + enrollmentRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(postPanel_Grade);
						revalidate();
						repaint();
					} else if (!postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("A")
							&& !postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("B")
							&& !postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("C")
							&& !postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("D")
							&& !postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("F")
							&& !postGradePanel.getPostPanel_NewGradeText().toUpperCase().contentEquals("N")) {
						JOptionPane.showMessageDialog(null,
								"Please enter only A, B, C, D, F, or N as grade input.  N is for no grade.");
						remove(postPanel_Grade);
						revalidate();
						repaint();
					} else {
						int number = Integer.parseInt(postGradePanel.getPostPanel_EIDText()) - 1;
						try {
							enrollmentRecord.moveFilePointer(number);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						Enrollment temp = null;
						try {
							temp = enrollmentRecord.readEnrollmentFile();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						String newGrade = postGradePanel.getPostPanel_NewGradeText().toUpperCase();
						temp.setGrade(newGrade);
						try {
							enrollmentRecord.moveFilePointer(number);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							enrollmentRecord.writeEnrollmentFile(temp);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						remove(postPanel_Grade);
						revalidate();
						repaint();
						postPanel_Grade = new JPanel();
						try {
							postGradePanel = new PostGradePanel();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						postButton_PostPanel_Grade = new JButton("Post");
						postButton_PostPanel_Grade.addActionListener(new PostButton_PostPanelListener_Grade());

						cancelButton_PostPanel_Grade = new JButton("Cancel");
						cancelButton_PostPanel_Grade.addActionListener(new CancelButton_PostPanelListener_Grade());

						postPanel_Grade.setLayout(new GridLayout(2, 1));
						buttonPanel_PostPanel_Grade = new JPanel();
						buttonPanel_PostPanel_Grade.add(postButton_PostPanel_Grade);
						buttonPanel_PostPanel_Grade.add(cancelButton_PostPanel_Grade);
						postPanel_Grade.add(postGradePanel);
						postPanel_Grade.add(buttonPanel_PostPanel_Grade);

						add(postPanel_Grade);
						revalidate();
						repaint();

						try {
							fillTextArea();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (HeadlessException | NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	// Report Listeners
	private class reportPrintListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			revalidate();
			repaint();

			try {
				enrollmentRecord = new EnrollmentFile("enrollment.dat");

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			mainPanel_Report = new JPanel();
			reportPrintPanel = new ReportPrintPanel();
			mainPanel_Report.setLayout(new GridLayout(2, 1));
			printButton_MainPanel_Report = new JButton("Print");
			printButton_MainPanel_Report.addActionListener(new printButton_MainPanelListener_Report());
			JPanel buttonPanel = new JPanel();

			buttonPanel.add(printButton_MainPanel_Report);

			cancelButton_MainPanel_Report = new JButton("Cancel");
			cancelButton_MainPanel_Report.addActionListener(new CancelButton_MainPanelListener_Report());

			buttonPanel.add(cancelButton_MainPanel_Report);
			mainPanel_Report.add(reportPrintPanel);
			mainPanel_Report.add(buttonPanel);

			add(mainPanel_Report);
			setVisible(true);
			revalidate();
			repaint();

		}
	}

	private class CancelButton_MainPanelListener_Report implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remove(mainPanel_Report);
			revalidate();
			repaint();
		}
	}

	private class printButton_MainPanelListener_Report implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int CIDValue = 0;
			if (!reportPrintPanel.getMainPanel_ReportPrint_CourseText().isEmpty())
				CIDValue = Integer.parseInt(reportPrintPanel.getMainPanel_ReportPrint_CourseText());
			if (reportPrintPanel.getMainPanel_ReportPrint_CourseText().isEmpty()
					|| reportPrintPanel.getMainPanel_ReportPrint_YearText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Cannot have an empty field. Input data purged.");
				remove(mainPanel_Report);
				revalidate();
				repaint();
			} else
				try {
					courseRecord = new CourseFile("course.dat");
					if (CIDValue > courseRecord.getNumberOfRecords() || CIDValue == 0) {
						JOptionPane.showMessageDialog(null,
								"Course # input value of " + CIDValue + " is higher than the actual stored"
										+ " records value of " + courseRecord.getNumberOfRecords()
										+ ". Value also cannot be 0.");
						remove(mainPanel_Report);
						revalidate();
						repaint();
					}

					else {
						String year = reportPrintPanel.getMainPanel_ReportPrint_YearText();
						int cNum = Integer.parseInt(reportPrintPanel.getMainPanel_ReportPrint_CourseText());

						remove(mainPanel_Report);
						revalidate();
						repaint();
						mainPanel_Report = new JPanel();
						reportPrintPanel = new ReportPrintPanel();
						mainPanel_Report.setLayout(new GridLayout(2, 1));
						printButton_MainPanel_Report = new JButton("Print");
						printButton_MainPanel_Report.addActionListener(new printButton_MainPanelListener_Report());
						JPanel buttonPanel = new JPanel();

						buttonPanel.add(printButton_MainPanel_Report);

						cancelButton_MainPanel_Report = new JButton("Cancel");
						cancelButton_MainPanel_Report.addActionListener(new CancelButton_MainPanelListener_Report());

						buttonPanel.add(cancelButton_MainPanel_Report);
						mainPanel_Report.add(reportPrintPanel);
						mainPanel_Report.add(buttonPanel);

						add(mainPanel_Report);
						setVisible(true);
						revalidate();
						repaint();

						try {
							fillReportTextArea(year, cNum);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (HeadlessException | NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		StudentManagementSystem pierce = new StudentManagementSystem();

	}

}
