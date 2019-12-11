package frplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Loader {

    private static String DATA_PROCESS = "";

    public static void main(String[] args) {
        boolean open = haveSteam();
        if(open){
            checkSteam();
        }
    }

    private static void checkSteam(){
        try {
            Process process = Runtime.getRuntime().exec(DATA_PROCESS);
            System.out.println(DATA_PROCESS);
            if(process.isAlive()){
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

                OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream());
                osw.write("?");
                osw.flush();
                osw.close();

                String line;
                while (true){
                    if((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean haveSteam() {
        String line;
        int instances = 0;
        try {
            Process processo = Runtime.getRuntime().exec("wmic.exe");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(processo.getInputStream()))) {
                OutputStreamWriter osw = new OutputStreamWriter(processo.getOutputStream());
                osw.write("process where name='steam.exe'");
                osw.flush();
                osw.close();
                while ((line = br.readLine()) != null) {
                    if (line.toLowerCase().contains("steam.exe")) {
                        instances++;
                        String[] datas = line.split("  ");
                        DATA_PROCESS = datas[1];
                    }
                }
                System.out.println("Existem " + instances + " processos steam.exe rodando");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if(instances <= 0)
            return false;
        return true;
    }

}
