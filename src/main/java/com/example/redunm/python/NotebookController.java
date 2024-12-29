package com.example.redunm.python;

import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/notebook")
public class NotebookController {

    @PostMapping("/execute")
    public String executeNotebook(@RequestParam String notebookPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python",
                    "python/execute_notebook.py",
                    notebookPath
            );
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Notebook 실행 성공: " + output.toString();
            } else {
                return "Notebook 실행 실패: " + output.toString();
            }
        } catch (Exception e) {
            return "오류 발생: " + e.getMessage();
        }
    }
}
