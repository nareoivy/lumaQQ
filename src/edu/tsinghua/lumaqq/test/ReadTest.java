package edu.tsinghua.lumaqq.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            File inputFile = new File("D:\\Temp\\Download\\Software\\zuo.eip");

            File extractDir = new File(new File("out"), inputFile.getName()
                    + ".extract");

            extract(inputFile, extractDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param extractFile
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void extract(File inputFile, File extractDir) throws IOException,
            FileNotFoundException {
        POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(
                inputFile));

        extractDir.mkdir();
        mkdir(extractDir, system.getRoot());
    }

    private static void writeOut(File parent, DocumentEntry doc) {
        byte[] buffer = new byte[8192];

        try {
            FileOutputStream out = new FileOutputStream(new File(parent, doc
                    .getName()));
            DocumentInputStream in = new DocumentInputStream(doc);

            int size = in.read(buffer);
            if (size == buffer.length) {
                out.write(buffer);
            } else {
                out.write(buffer, 0, size);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mkdir(File parent, DirectoryEntry root) {
        for (Iterator it = root.getEntries(); it.hasNext();) {
            Entry entry = (Entry) it.next();

            if (entry.isDirectoryEntry()) {
                DirectoryEntry dir = (DirectoryEntry) entry;

                File subDir = new File(parent, dir.getName());
                subDir.mkdir();
                mkdir(subDir, dir);
            } else if (entry.isDocumentEntry()) {
                DocumentEntry doc = (DocumentEntry) entry;

                writeOut(parent, doc);
            } else {
                // nothing
            }
        }
    }
}
