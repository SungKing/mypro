package org.warnsun.generate.dao;

import lombok.NonNull;
import org.springframework.util.CollectionUtils;
import org.warnsun.models.ColumnTypes;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示表的数据结构的SQL
 * show columns from 表名
 * describe 表名
 *
 * @author song.wang
 * @create 2017/7/31 10:19
 */
public class GenerateDao {

    private GenerateDao() {
    }

    public static void start(String url, String user, String password, @NonNull String tableName, String path, String tenPath) {
        try {
            List<ColumnTypes> columnTypes = queryColns(url, user, password, tableName);
            writeModel(columnTypes, path, tableName);
            writeQurtyArgs(columnTypes, path, tableName);
            writeDaoInterface(columnTypes, path, tableName, tenPath+File.separator+"dao.java");
            writeDaoImpl(columnTypes,path,tableName,tenPath+File.separator+"daoImpl.java");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ColumnTypes> queryColns(String url, String user, String password, @NonNull String tableName) throws SQLException {
       /* try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        Class<Driver> driverClass = Driver.class;//装载类
        ArrayList<ColumnTypes> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement ps = connection.createStatement()) {
                try (ResultSet resultSet = ps.executeQuery( String.format("DESCRIBE %s",tableName))) {
                    while (resultSet.next()) {
                        ColumnTypes columnTypes = new ColumnTypes();
                        columnTypes.setField(resultSet.getString("Field"));
                        columnTypes.setType(resultSet.getString("Type"));
                        columnTypes.setAllowNull(resultSet.getString("Null"));
                        columnTypes.setKey(resultSet.getString("Key"));
                        columnTypes.setDefaultVal(resultSet.getString("Default"));
                        columnTypes.setExtra("Extra");
                        list.add(columnTypes);
                    }
                }
            }
        }

        return list;
    }

    /**
     * 写model
     *
     * @param list
     * @param path
     * @param tableName
     * @throws IOException
     */
    public static void writeModel(List<ColumnTypes> list, @NonNull String path, @NonNull String tableName) throws IOException {
        File file = new File(path + File.separator + firstToUp(toSmallHump(tableName)) + ".java");
        boolean flag = false;
        if (!file.exists())
            flag = file.createNewFile();
        if (!flag)
            return;
        try (FileWriter fileWriter = new FileWriter(file)) {
            try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
                writer.write("import lombok.Data;");
                writer.newLine();
                writer.write("import mtime.lark.db.jsd.NameStyle;");
                writer.newLine();
                writer.write("import java.time.ZonedDateTime;");
                writer.newLine();
                writer.write("import mtime.lark.db.jsd.annotation.JsdTable;");
                writer.newLine();
                writer.newLine();
                writer.newLine();

                writer.write("@Data");
                writer.newLine();
                writer.write("@JsdTable(nameStyle = NameStyle.LOWER)");
                writer.newLine();
                writer.write(String.format("public class %s {", firstToUp(toSmallHump(tableName))));
                writer.newLine();
                for (ColumnTypes column : list) {
                    writer.write(String.format("\tprivate %s %s;",dbType2JavaType(column.getType()),toSmallHump(column.getField())));
                    writer.newLine();
                }
                writer.write("}");
            }
        }
    }

    /**
     * 写查询参数
     *
     * @param list
     * @param path
     * @param tableName
     * @throws IOException
     */
    public static void writeQurtyArgs(List<ColumnTypes> list, @NonNull String path, @NonNull String tableName) throws IOException {
        File file = new File(path + File.separator + firstToUp(toSmallHump(tableName)) + "Args.java");
        boolean flag = false;
        if (!file.exists())
            flag = file.createNewFile();
        if (!flag)
            return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("import lombok.Data;");
            writer.newLine();
            writer.write("import java.time.LocalDateTime;");
            writer.newLine();
            writer.write("import java.util.List;");
            writer.newLine();

            writer.write("@Data");
            writer.newLine();
            writer.write(String.format("public class %sArgs {", firstToUp(toSmallHump(tableName))));
            writer.newLine();
            if (!CollectionUtils.isEmpty(list)) {
                for (ColumnTypes column : list) {
                    StringBuilder listGen = new StringBuilder();
                    StringBuilder maxGen = new StringBuilder();
                    StringBuilder minGen = new StringBuilder();
                    listGen.append("private ");
                    maxGen.append("private ");
                    minGen.append("private ");

                    String javaType = dbType2JavaType(column.getType());
                    listGen.append(String.format("List<%s> ",javaType));
                    maxGen.append(String.format("%s ",javaType));
                    minGen.append(String.format("%s ",javaType));

                    String fieldName = toSmallHump(column.getField());
                    listGen.append(String.format("%s%s;", fieldName, "List"));
                    maxGen.append(String.format("%s%s;", fieldName, "Max"));
                    minGen.append(String.format("%s%s;", fieldName, "Min"));
                    writer.write("\t//" + fieldName);
                    writer.newLine();
                    writer.write("\t");
                    writer.write(listGen.toString());
                    writer.newLine();
                    writer.write("\t");
                    writer.write(maxGen.toString());
                    writer.newLine();
                    writer.write("\t");
                    writer.write(minGen.toString());
                    writer.newLine();
                }
            }

            writer.write("}");
        }
    }

    /**
     * 写dao 的接口定义
     * @param list
     * @param path
     * @param tableName
     * @param temepletePath
     * @throws IOException
     */
    public static void writeDaoInterface(List<ColumnTypes> list,@NonNull String path, @NonNull String tableName, @NonNull String temepletePath) throws IOException {
        File file = new File(path + File.separator + firstToUp(toSmallHump(tableName)) + "Dao.java");
        writeFileByTemplete(list,tableName, temepletePath, file);
    }

    /**
     * 根据模板写相应的文件
     * @param list
     * @param tableName
     * @param temepletePath
     * @param file
     * @throws IOException
     */
    private static void writeFileByTemplete(List<ColumnTypes> list,@NonNull String tableName, @NonNull String temepletePath, File file) throws IOException {
        boolean flag = false;
        if (!file.exists())
            flag = file.createNewFile();
        if (!flag)
            return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(temepletePath))) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String replace = line.replace("{TableName}", firstToUp(toSmallHump(tableName)))
                            .replace("{table_name}",tableName)
                            .replace("{time}", LocalDateTime.now().toString())
                            .replace("{author}", System.getProperty("user.name"))
                            .replace("{pkArgs}",getPkArgs(list))
                            .replace("{pkFilter}",getPkFilter(list))
                            .replace("{pkColumns}",getPkColumns(list))
                            ;


                    if (temepletePath.contains("Impl.java") && replace.contains("{field}")){
                        replace = "";
                        writeFinalField(list,writer);
                    }
                    if (replace.contains("{for_filter}")){
                        replace="";
                        writeFilterArgs(list,writer);
                    }
                    writer.write(replace);
                    writer.newLine();
                }
            }
        }
    }

    private static String getPkColumns(List<ColumnTypes> list) {
        StringBuilder sb = new StringBuilder();
        for (ColumnTypes columnTypes : list) {
            if (columnTypes.getKey() !=null && columnTypes.getKey().equals("PRI")){
                sb.append(columnTypes.getField().toUpperCase());
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private static String getPkFilter(List<ColumnTypes> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("f");
        for (ColumnTypes columnTypes : list) {
            if (columnTypes.getKey() !=null && columnTypes.getKey().equals("PRI")){
                sb.append(String.format(".add(%s, %s)",columnTypes.getField().toUpperCase(),toSmallHump(columnTypes.getField())));
            }
        }
        sb.append(";");
        return sb.toString();
    }

    /**
     * 获得主键
     * @param list
     * @return
     */
    private static String getPkArgs(List<ColumnTypes> list) {
        StringBuilder sb = new StringBuilder();
        for (ColumnTypes columnTypes : list) {
            if (columnTypes.getKey() !=null && columnTypes.getKey().equals("PRI")){
                sb.append(String.format("%s %s,",dbType2JavaType(columnTypes.getType()),toSmallHump(columnTypes.getField())));
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * 写字段名称
     * @param list
     * @param writer
     * @throws IOException
     */
    private static void writeFinalField(@NonNull List<ColumnTypes> list, @NonNull BufferedWriter writer)throws IOException{
        for (ColumnTypes columnTypes : list) {
            writer.write(String.format("\tprivate static final String %s = \"%s\";",columnTypes.getField().toUpperCase(),columnTypes.getField()));
            writer.newLine();
        }

    }

    /**
     * 写 filter 里面的东西
     * @param list
     * @param writer
     */
    private static void writeFilterArgs(@NonNull List<ColumnTypes> list, @NonNull BufferedWriter writer)throws IOException {
        for (ColumnTypes columnTypes : list) {
            String name = firstToUp(toSmallHump(columnTypes.getField()));
            writer.newLine();
            writer.write(String.format("\t\tif (!CollectionUtils.isEmpty(args.get%sList())){",name ) );
            writer.newLine();
            writer.write(String.format("\t\t\tf.add(%s, FilterType.IN,args.get%sList().toArray(new %s[]{}));",columnTypes.getField().toUpperCase(),name,dbType2JavaType(columnTypes.getType())));
            writer.newLine();
            writer.write("\t\t}else{");
            writer.newLine();
            writer.write(String.format("\t\t\tif (args.get%sMax()!=null)",name ) );
            writer.newLine();
            writer.write(String.format("\t\t\t\tf.add(%s,FilterType.LTE,args.get%sMax());",columnTypes.getField().toUpperCase(),name));
            writer.newLine();
            writer.write(String.format("\t\t\tif (args.get%sMin()!=null)",name ) );
            writer.newLine();
            writer.write(String.format("\t\t\t\tf.add(%s,FilterType.GTE,args.get%sMin());",columnTypes.getField().toUpperCase(),name));
            writer.newLine();
            writer.write("\t\t}");
            writer.newLine();
        }
    }

    /**
     *
     * @param list
     * @param path
     * @param tableName
     * @param temepletePath
     * @throws IOException
     */
    public static void writeDaoImpl(List<ColumnTypes> list,@NonNull String path, @NonNull String tableName, @NonNull String temepletePath)throws IOException{
        File file = new File(path + File.separator + firstToUp(toSmallHump(tableName)) + "DaoImpl.java");
        writeFileByTemplete(list,tableName, temepletePath, file);
    }

    /**
     * 将下划线命名方法转换为小驼峰
     *
     * @return
     */
    public static String toSmallHump(@NonNull String name) {
        char[] chars = name.toCharArray();
        boolean flag = false;
        for (int i = 0; i < chars.length; i++) {
            if (flag && chars[i] >= 97 && chars[i] <= 122) {
                chars[i] = (char) (chars[i] - 32);
            }
            if (chars[i] == '_') {
                flag = true;
                chars[i] = 0;
            } else
                flag = false;
        }
        String[] split = new String(chars).split(new String(new char[]{0}));
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String firstToUp(@NonNull String name) {
        char[] chars = name.toCharArray();
        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] -= 32;
        }
        return new String(chars);
    }

    private static String dbType2JavaType(@NonNull String dbType){
        if (dbType.contains("bigint")) {
            return "Long";
        } else if (dbType.contains("int")) {
            return "Integer";

        } else if (dbType.contains("time") || dbType.contains("date")) {
            return "ZonedDateTime";
        } else if (dbType.contains("char")) {
            return "String";
        } else if (dbType.contains("decimal")) {
            return "BigDecimal";
        }
        return "Object";
    }
}
