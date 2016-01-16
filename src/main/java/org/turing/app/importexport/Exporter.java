package org.turing.app.importexport;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.json.simple.JSONObject;
import org.turing.app.exceptions.ImportExportException;
import org.turing.support.Logger;

import java.io.File;
import java.io.IOException;

public class Exporter {

    private final ProgramExporter programExporter;
    private final TapeExporter tapeExporter;

    public Exporter(ProgramExporter programExporter, TapeExporter tapeExporter) {
        this.programExporter = programExporter;
        this.tapeExporter = tapeExporter;
    }

    public void exportProgramToFile(String filename) {
        JSONObject jsonData = programExporter.getProgramDataAsJson();
        exportJsonObjectToFile(filename, jsonData);
    }

    public void exportTapeToFile(String filename) {
        JSONObject jsonData = tapeExporter.getTapeDataAsJson();
        exportJsonObjectToFile(filename, jsonData);
    }

    private void exportJsonObjectToFile(String filename, JSONObject jsonData) {
        try {
            Files.write(jsonData.toJSONString(), new File(filename), Charsets.UTF_8);
        } catch (IOException e) {
            Logger.error(e);
            throw new ImportExportException("Export to file failed");
        }
    }
}
