import java.io.*;
import java.util.*;

class program733
{
    public static void main(String A[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);
        String PackFileName = null;
        File fpackobj = null;
        FileInputStream fiobj = null;
        FileOutputStream foobj = null;
        byte Header[] = new byte[100];
        String strHeader = null;
        String Tokens[] = null;
        File NewFile = null;
        byte Buffer[] = null;
        int bytesRead = 0;

        System.out.println("Enter the name of packed file : ");
        PackFileName = sobj.nextLine();

        fpackobj = new File(PackFileName);

        if (fpackobj.exists())
        {
            fiobj = new FileInputStream(fpackobj);

            // Read Header
            while ((bytesRead = fiobj.read(Header, 0, 100)) != -1)
            {
                // Use only the bytes actually read, not stale leftover data
                strHeader = new String(Header, 0, bytesRead);

                System.out.println("Header is : " + strHeader);

                strHeader = strHeader.trim();
                strHeader = strHeader.replaceAll("\\s+", " ");

                // Skip empty/garbage headers (e.g. trailing blank reads)
                if (strHeader.isEmpty())
                {
                    continue;
                }

                Tokens = strHeader.split(" ");

                // Basic safety check before accessing tokens
                if (Tokens.length < 2)
                {
                    System.out.println("Invalid header, skipping...");
                    continue;
                }

                String fileName = Tokens[0];
                int fileSize;
                try
                {
                    fileSize = Integer.parseInt(Tokens[1]);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid size in header, skipping...");
                    continue;
                }

                System.out.println("File name : " + fileName);
                System.out.println("File size : " + fileSize);

                NewFile = new File(fileName);
                NewFile.createNewFile();

                foobj = new FileOutputStream(NewFile);

                Buffer = new byte[fileSize];

                // Read Data (loop to guarantee all bytes are read)
                int totalRead = 0;
                while (totalRead < fileSize)
                {
                    int n = fiobj.read(Buffer, totalRead, fileSize - totalRead);
                    if (n == -1)
                    {
                        break; // packed file ended unexpectedly
                    }
                    totalRead += n;
                }

                // Write the Data
                foobj.write(Buffer, 0, totalRead);

                // Close this file's output stream before moving to next header
                foobj.close();
            }

            // Close input stream after loop ends
            fiobj.close();
        }
        else
        {
            System.out.println("There is no such pack file");
        }

        sobj.close();
    }
}