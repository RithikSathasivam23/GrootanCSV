package com.sentiacare;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.opencsv.CSVReader;
import io.javalin.core.util.FileUtil;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Services {

    private static Connection con;
    private static String DATABASE = "csvdb1";
    private static String TABLENAME = "csvtable";
    private static int Success = 0;
    private static int Failure = 0;


    public static String uploadcsv(Context context) {
        try{
            Success = 0;
            Failure = 0;
            UploadedFile file = context.uploadedFile("files");
            FileUtil.streamToFile(file.getContent(), "/" + file.getFilename() + ".csv");
            FileReader f = new FileReader("/" + file.getFilename() + ".csv");

            String status = startCSVtoMySQL(f);
            Map<String,String> map = new HashMap<>();
            map.put("Status",status);
            map.put("Success", String.valueOf(Success));
            map.put("Failure", String.valueOf(Failure));

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson json = gsonBuilder.create();
            f.close();
            return json.toJson(map);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String startCSVtoMySQL(FileReader f) {
        try{
            CSVReader csvReader = new CSVReader(f, ',');

            con = DBConnection.getconnection();
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS "+DATABASE);
            con = DBConnection.getconnection(DATABASE);
            List<String[]> list = csvReader.readAll();
            if (list.size()>0){
                String[] heading = list.get(0);
                if (createtable(heading)){
                    StringBuilder insertsql = new StringBuilder(" INSERT INTO `"+DATABASE+"`.`" + TABLENAME + "` ( ");
                    String colnames = createColumnValue(heading);
                    insertsql.append(colnames).append(" ) VALUES (");
                    for (int i =1;i<list.size();i++){
                        try{
                            String colvalues = createColumnData(list.get(i));
                            String sql = insertsql.toString() + colvalues + ");";
                            boolean res = statement.execute(sql);
                            if (!res){
                                Success = Success +1;
                            }
                        }catch (Exception e){
                            Failure = Failure + 1;
                        }
                    }
                    return Status.Success;
                }else {
                    return Status.TableCreationError;
                }
            }else {
                return Status.NoRecordFound;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Status.Failure;
        }
    }

    private static String createColumnValue(String[] heading) {
        try {
            StringBuilder colName = new StringBuilder();
            for (String s: heading){
                colName.append("`").append(s.trim()).append("`, ");
            }
            colName.deleteCharAt(colName.lastIndexOf(","));
            return colName.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private static String createColumnData(String[] heading) {
        try {
            StringBuilder colName = new StringBuilder();
            for (String s: heading){
                colName.append("\"").append(s.trim()).append("\", ");
            }
            colName.deleteCharAt(colName.lastIndexOf(","));
            return colName.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private static boolean createtable(String[] heading) {
        try{
            Statement statement = con.createStatement();
            String createsql = "Create TABLE IF NOT EXISTS `"+TABLENAME+"` (";
            StringBuilder colsql = new StringBuilder();
            for (String s: heading){
                colsql.append(" `").append(s.trim()).append("` VARCHAR(150) NULL, ");
            }
            colsql.deleteCharAt(colsql.lastIndexOf(","));
            createsql = createsql+colsql+");";

            boolean result = statement.execute(createsql);
            return !result;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
