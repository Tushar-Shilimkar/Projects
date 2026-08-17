import java.awt.*;
import java.io.*;
import javax.swing.*;

class program733GUI
{
    static JTextArea outputArea;
    static JTextField txtFilePath;

    public static void main(String A[]) throws Exception
    {
        JFrame frame = new JFrame("File Unpacker");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtFilePath = new JTextField();
        JButton btnBrowse = new JButton("Browse...");
        JButton btnUnpack = new JButton("Unpack");
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(txtFilePath, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnBrowse);
        btnPanel.add(btnUnpack);
        topPanel.add(btnPanel, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        frame.setVisible(true);

        // Browse button -> pick file, put path in text field (jaga Scanner cha)
        btnBrowse.addActionListener(e ->
        {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
            {
                txtFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Unpack button -> same original logic call
        btnUnpack.addActionListener(e ->
        {
            outputArea.setText("");
            try
            {
                unpack(txtFilePath.getText());
            }
            catch (Exception ex)
            {
                outputArea.append("Error: " + ex.getMessage() + "\n");
            }
        });
    }

    // ---- Original logic same as before, System.out.println -> log() ----
    static void unpack(String PackFileName) throws Exception
    {
        File fpackobj = null;
        FileInputStream fiobj = null;
        FileOutputStream foobj = null;
        byte Header[] = new byte[100];
        String strHeader = null;
        String Tokens[] = null;
        File NewFile = null;
        byte Buffer[] = null;
        int bytesRead = 0;

        fpackobj = new File(PackFileName);

        if (fpackobj.exists() && fpackobj.isFile())
        {
            fiobj = new FileInputStream(fpackobj);

            // Read Header
            while ((bytesRead = fiobj.read(Header, 0, 100)) != -1)
            {
                strHeader = new String(Header, 0, bytesRead);

                log("Header is : " + strHeader);

                strHeader = strHeader.trim();
                strHeader = strHeader.replaceAll("\\s+", " ");

                if (strHeader.isEmpty())
                {
                    continue;
                }

                Tokens = strHeader.split(" ");

                if (Tokens.length < 2)
                {
                    log("Invalid header, skipping...");
                    continue;
                }

                int fileSize;
                try
                {
                    fileSize = Integer.parseInt(Tokens[1]);
                }
                catch (NumberFormatException ex)
                {
                    log("Invalid size in header, skipping...");
                    continue;
                }

                log("File name : " + Tokens[0]);
                log("File size : " + fileSize);

                NewFile = new File(fpackobj.getParent(), Tokens[0]);
                NewFile.createNewFile();

                foobj = new FileOutputStream(NewFile);

                Buffer = new byte[fileSize];

                // Read Data
                int totalRead = 0;
                while (totalRead < fileSize)
                {
                    int n = fiobj.read(Buffer, totalRead, fileSize - totalRead);
                    if (n == -1) break;
                    totalRead += n;
                }

                // Write the Data
                foobj.write(Buffer, 0, totalRead);
                foobj.close();

                log("Extracted : " + NewFile.getAbsolutePath());
            } // end of While

            fiobj.close();
            log("\nUnpacking complete.");
        }
        else
        {
            log("There is no such pack file (or it is a directory)");
        }
    }

    static void log(String msg)
    {
        outputArea.append(msg + "\n");
    }
}