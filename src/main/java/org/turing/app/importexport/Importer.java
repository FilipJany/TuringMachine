package org.turing.app.importexport;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.turing.app.exceptions.ImportExportException;
import org.turing.support.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Importer {

    private final ProgramImporter programImporter;
    private final TapeImporter tapeImporter;

    public Importer(ProgramImporter programImporter, TapeImporter tapeImporter) {
        this.programImporter = programImporter;
        this.tapeImporter = tapeImporter;
    }

    public void importProgramFromFile(File file) {
        JSONObject jsonObject = readJson(file);

        programImporter.readIntoModel(jsonObject);
    }

    public void importTapeFromFile(File file) {
        JSONObject jsonObject = readJson(file);

        tapeImporter.readIntoModel(jsonObject);
    }

    private JSONObject readJson(File file) {
        try {
            return (JSONObject) JSONValue.parseWithException(getReader(file));
        } catch (IOException | ParseException e) {
            Logger.error(e);
            throw new ImportExportException("Import failed");
        }
    }

    private BufferedReader getReader(File file) {
        try {
            return Files.newReader(file, Charsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new ImportExportException("Specified file: " + file + " does not exist");
        }
    }

}
