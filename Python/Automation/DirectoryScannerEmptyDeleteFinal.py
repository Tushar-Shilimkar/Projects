###################################################################
#   
#   Importing required libraries
#
###################################################################

import sys
import os
import time
import schedule

###################################################################
#   
#   Function name :     DirectoryScanner
#   Input :             Name of Directory
#   Description :       Delete all Empty files periodically
#   Date :              19/07/2026
#   Author :            Tushar Vijay Shilimkar
#
###################################################################

def DirectoryScanner(DirectoryPath):
    Border = "*"*60
    timestamp = time.ctime()

    LogFileName = "JayGanesh%s.log"%(timestamp)
    LogFileName = LogFileName.replace(" ","_")
    LogFileName = LogFileName.replace(":","_")

    Ret = False

    Ret = os.path.exists(DirectoryPath)

    if(Ret == False):
        print("JayGanesh - Automation Error : There is no such Directory with name ", DirectoryPath)
        return

    Ret = os.path.isdir(DirectoryPath)

    if(Ret == False):
        print("JayGanesh - Automation Error : It is not a directory with name", DirectoryPath)
        return

    print("Log File gets created with name :",LogFileName)

    fobj = open(LogFileName,"w")

    fobj.write(Border+"\n")
    fobj.write("--------------- RajgadGuard Automation Script ------------------\n")
    fobj.write(Border+"\n\n")

    fobj.write("Files from the Directory are : \n\n")
    fobj.write(Border+"\n")

    TotalFiles = 0
    EmptyFiles = 0

    for FolderName, SubFolder, FileName in os.walk(DirectoryPath):
        for fname in FileName:
            TotalFiles = TotalFiles + 1

            fname = os.path.join(FolderName, fname)
            fobj.write(f"{fname} : {os.path.getsize(fname)} bytes\n")

            if(os.path.getsize(fname) == 0):
                EmptyFiles = EmptyFiles + 1
                os.remove(fname)

    fobj.write(Border+"\n")
    fobj.write(f"Total Files Scanned : {TotalFiles}\n")
    fobj.write(f"Total Empty Files found and Deleted : {EmptyFiles}\n")

    fobj.write(Border+"\n")
    fobj.write("Log file gets created at :"+timestamp)
    fobj.write("\n"+Border+"\n")

    fobj.close()

###################################################################
#   
#   Function name :     main
#   Input :             Command line arguments
#   Description :       It controls the script
#   Date :              19/07/2026
#   Author :            Tushar Vijay Shilimkar
#
###################################################################

def main():
    Border = "*"*63
    print(Border)
    print("--------------- RajgadGuard Automation Script ------------------")
    print (Border)
    if(len(sys.argv) == 2):
        if(sys.argv[1] == "--h" or sys.argv[1] == "--H"):
            print("This Automation is used to travel the Directory")
            print("For better usage please check --u flag")
        elif(sys.argv[1] == "--u" or sys.argv[1] == "--U"):
            print("Please execute the Script as")
            print("Python FileName.py DirectoryName")
            print("DirectoryName should be absolute path")
        else:
            schedule.every(1).minute.do(DirectoryScanner, sys.argv[1])
            DirectoryScanner(sys.argv[1])

            while True:
                schedule.run_pending()
                time.sleep(1)
    else:
        print("Invalid number of arguments")
        print("Please use --h or --u for more Information")

    print (Border)
    print("------ Thank you for using RajgadGuard Automation Script ------")
    print (Border)

###################################################################
#   
#   Starter of the automation script
#
###################################################################

if __name__ == "__main__":
    main()

