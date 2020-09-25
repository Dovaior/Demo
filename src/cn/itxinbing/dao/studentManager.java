package cn.itxinbing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class studentManager {
    private static Scanner scanner = new Scanner(System.in);
    // 循环大菜单的操作值
    public static int i = 1;
    //申明
    public static DBHelper helper = new DBHelper();

    /**
     * 菜单打印
     */
    public static void menu() {
        System.out.println("-----------学生管理系统--------------");
        System.out.println("-         1.增加学生信息              -");
        System.out.println("-         2.删除学生信息              -");
        System.out.println("-         3.更改学生信息              -");
        System.out.println("-         4.查找单个学生信息       -");
        System.out.println("-         5.查找全部学生信息       -");
        System.out.println("-         6.退出                            -");
    }

    public static void main(String[] args) {
        while (i == 1) {
            menu();
            System.out.println("请输入操作数：");
            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    addStu();
                    break;
                case 2:
                    deleteStu();
                    break;
                case 3:
                    updateStu();
                    break;
                case 4:
                    selectStuById();
                    break;
                case 5:
                    selectAllStu();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        }

    }

    /**
     * 增加操作
     */
    private static void addStu() {
        int j = 1;
        while (j == 1) {
            System.out.println("请输入姓名：");
            String name = scanner.next();
            System.out.println("请输入年龄：");
            int age = scanner.nextInt();
            System.out.println("请输入性别：");
            String gender = scanner.next();
            String sql = "insert into student(name,age,gender) values (?,?,?)";
            Object[] objs = {name, age, gender};
            try {
                helper.executeUpdate(sql, objs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("是否继续：1.继续      2.返回菜单");
            j = scanner.nextInt();
            if (j == 1) {
                j = 1;
            } else if (j == 2) {
                try {
                    helper.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i = 1;
            } else {
                System.out.println("输入有误，请重新选择！");
            }
        }
    }

    /**
     * 删除操作
     */
    private static void deleteStu() {
        int j = 1;
        while (j == 1) {
            try {
                String sql = "select * from student";
                ResultSet rs = helper.executeQuery(sql);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    System.out.println(id + "     " + name + "     " + age + "     " + gender);
                }
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("请输入要删除学生的id");
            int stuId = scanner.nextInt();
            String sql1 = "delete from student where stuid=?";
            Object[] objs = {stuId};
            try {
                helper.executeUpdate(sql1, objs);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("删除成功！");
            System.out.println("是否继续：1.继续      2.返回菜单");
            j = scanner.nextInt();
            if (j == 1) {
                j = 1;
            } else if (j == 2) {
                try {
                    helper.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i = 1;
            } else {
                System.out.println("输入有误，请重新选择！");
            }
        }
    }

    /**
     * 更改操作
     */
    private static void updateStu() {
        int j = 1;
        while (j == 1) {
            System.out.println("请输入要更改的学生id");
            int stuid = scanner.nextInt();
            System.out.println("请输入姓名：");
            String name = scanner.next();
            System.out.println("请输入年龄：");
            int age = scanner.nextInt();
            System.out.println("请输入性别：");
            String gender = scanner.next();
            String sql = "update student set name=?,age=?,gender=? where stuid=?";
            Object[] objs = {name, age, gender, stuid};
            try {
                helper.executeUpdate(sql, objs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("修改成功！");
            System.out.println("是否继续：1.继续      2.返回菜单");
            j = scanner.nextInt();
            if (j == 1) {
                j = 1;
            } else if (j == 2) {
                try {
                    helper.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i = 1;
            } else {
                System.out.println("输入有误，请重新选择！");
            }
        }
    }

    /**
     * 通过id查找信息
     */
    private static void selectStuById() {
        int j = 1;
        while (j == 1) {
            System.out.println("请输入学生id：");
            int stuid = scanner.nextInt();
            String sql = "select * from student where stuid=?";
            Object[] objs = {stuid};
            try {
                ResultSet rs = helper.executeQuery(sql, objs);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int age = rs.getInt(3);
                    String gender = rs.getString(4);
                    System.out.println(id + "   " + name + "    " + age + "    " + gender);
                }
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("查询成功！");
            System.out.println("是否继续：1.继续      2.返回菜单");
            j = scanner.nextInt();
            if (j == 1) {
                j = 1;
            } else if (j == 2) {
                i = 1;
                try {
                    helper.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("输入有误，请重新选择！");
            }
        }
    }

    /**
     * 查找全部信息
     */
    private static void selectAllStu() {
        int j = 1;
        while (j == 1) {
            try {
                String sql = "select * from student";
                ResultSet rs = helper.executeQuery(sql);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    System.out.println(id + "     " + name + "     " + age + "     " + gender);
                }
                rs.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("查询成功！");
            System.out.println("是否继续：1.继续      2.返回菜单");
            j = scanner.nextInt();
            if (j == 1) {
                j = 1;
            } else if (j == 2) {
                i = 1;
                try {
                    helper.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("输入有误，请重新选择！");
            }
        }
    }

}