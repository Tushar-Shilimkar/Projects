# python ProcessSurvillence.py 2 MarvellousLog
# python ProcessSurvillence.py time_intervel Folder_Name
#                   0               1              2
#len(sys.argv) -> 3

# ProcessSurvillence.py --h
# ProcessSurvillence.py --u
#           0            1
#len(sys.argv) -> 2

###################################################################
#   
#   Importing required libraries
#
###################################################################

import psutil
import sys
import os
import time
import schedule

###################################################################
#   
#   Function name :     ProcessScan
#   Description :       It collects PID, name, username, status, CPU%, and memory%
#   Date :              26/07/2026
#   Author :            Tushar Vijay Shilimkar
#
###################################################################

def ProcessScan():
    listprocess = list()
    for proc in psutil.process_iter():
        info = proc.as_dict(attrs=["pid","name","username","status"])
        info["cpu_percent"] = proc.cpu_percent(None)
        info["memory_percent"] = proc.memory_percent()

        listprocess.append(info)

    return listprocess

###################################################################
#   
#   Function name :     PlatformSurvillence
#   Description :       Checks if the given folder exists; if not, creates it
#   Date :              26/07/2026
#   Author :            Tushar Vijay Shilimkar
#
###################################################################

def PlatformSurvillence(FolderName):
    Border = "*"*50

    Ret = False

    Ret = os.path.exists(FolderName)

    if(Ret == True):
        Ret = os.path.isdir(FolderName)

        if(Ret == False):
            print("Unable to proceed as folder name is existing but its not a directory")
            return
    else:
        os.mkdir(FolderName)
        print("Directory for the logfile gets created Succesfully")

    timestamp = time.strftime("%Y-%m-%d_%H-%M-%S")

    FileName = os.path.join(FolderName, "Marvellous_%s.log" %timestamp)

    fobj = open(FileName,"w")

    print(f"Log file gets succesfully created with name {FileName}")

    fobj.write(Border+"\n")
    fobj.write("----- PulseTrack Platform Survillence System-----\n")
    fobj.write("Log File gets created at :"+timestamp+"\n")
    fobj.write(Border+"\n\n")

    fobj.write("------------------- System Report -------------------\n")

    # CPU Information
    fobj.write("Number of active CPU cores : %s \n" %psutil.cpu_count())
    fobj.write("CPU Usage : %s %%\n" %psutil.cpu_percent())
    fobj.write(Border+"\n")

    # RAM Information
    memory =  psutil.virtual_memory()

    fobj.write("RAM Usage : %s %%\n" %memory.percent)
    fobj.write("Total RAM available : %s \n" %memory.total)

    fobj.write(Border+"\n")

    # Network Usage
    netobj = psutil.net_io_counters()

    fobj.write("Network Usage Report\n")
    fobj.write("Sent : %.2f MB\n" %(netobj.bytes_sent / (1024 * 1024)))
    fobj.write("Receive : %.2f MB\n" %(netobj.bytes_recv / (1024 * 1024)))

    # Process Log

    Data = ProcessScan()

    for info in Data:
        fobj.write("PID : %s\n" %info.get("pid"))
        fobj.write("Name : %s\n" %info.get("name"))
        fobj.write("User Name : %s\n" %info.get("username"))
        fobj.write("Status : %s\n" %info.get("status"))
        fobj.write("CPU Usage : %.2f\n" %info.get("cpu_percent"))
        fobj.write("RAM Usage : %.2f\n" %info.get("memory_percent"))

        fobj.write(Border+"\n")

    fobj.write(Border+"\n")
    fobj.write("------------------ End of Log File ------------------\n")
    fobj.write(Border+"\n")

    fobj.close()

###################################################################
#   
#   Function name :     main
#   Input :             Command line arguments
#   Description :       It controls the script
#   Date :              26/07/2026
#   Author :            Tushar Vijay Shilimkar
#
###################################################################

def main():
    Border = "*"*60
    print(Border)
    print("----- PulseTrack Platform Survillence System-----\n")
    print(Border)

    # --h & --u handling
    if(len(sys.argv) == 2):
        if(sys.argv[1] == "--h" or sys.argv[1] == "--H"):
            print("This automation Script is used to perform")
            print("1 : It fetch the information of running processes")
            print("2 : It fetch the information about the primary storage as RAM")
            print("3 : It fetch the information about the secondary storage as HHD")
            print("4 : It fetch the information about the microprocessor")
            print("5 : It gets auto schedule periodically")
            print("6 : It maintains all records into log file")
            print("7 : It send the log files through mail periodically")

        elif(sys.argv[1] == "--u" or sys.argv[1] == "--U"):
            print("Use the automation script as :")
            print(f"python {sys.argv[0]} Time_Interval Folder_Name")
            print("Time_Interval : Time in minutes for periodic execution")
            print("Folder_Name : Name of folder for the log file creation")

        else:
            print("Unable to proceed as argument are not watching.")
            print("Please use --h or --u flag for getting more datails.")

    # Actual project code
    elif (len(sys.argv) == 3):
        # print("CPU Usage : ",psutil.cpu_percent())
        print("Schedular started Succesfully")
        print("Press Ctrl + C to abort the automation script")

        schedule.every(int(sys.argv[1])).minutes.do(PlatformSurvillence, sys.argv[2])
        while True:
            schedule.run_pending()
            time.sleep(1)

    else:
        print("Invalid number of arguments.")
        print("Unable to proceed as argument are not watching.")
        print("Please use --h or --u flag for getting more datails.")

    print(Border)
    print("-----Thank you for using our Automation System-----")
    print(Border)

###################################################################
#   
#   Starter of the automation script
#
###################################################################

if __name__ == "__main__":
    main()