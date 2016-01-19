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

    public void exportProgramToFile(File file) {
        JSONObject jsonData = programExporter.getProgramDataAsJson();
        exportJsonObjectToFile(file, jsonData);
    }

    public void exportTapeToFile(File file) {
        JSONObject jsonData = tapeExporter.getTapeDataAsJson();
        exportJsonObjectToFile(file, jsonData);
    }

    private void exportJsonObjectToFile(File file, JSONObject jsonData) {
        try {
            Files.write(jsonData.toJSONString(), file, Charsets.UTF_8);
        } catch (IOException e) {
            Logger.error(e);
            throw new ImportExportException("Export to file failed");
        }
    }
}
