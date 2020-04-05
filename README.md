# MultithreadingFilesMover
Gets config from config.yaml. In this config you can specify number of thread (number of connections is equal) and some details on
which files should be processed and in what directories they should be moved.
Searches in database for files with specified ending and adds them into a list. 
When size of list meets the limit (specified in config) creates a thread which checks createDate of each file, creates a directory
(yyyy-mm-dd) and moves this file there.

The point of this application is that if you how really big amount of files in one directory then it might be hard to work with them
because even search starts to take a lot of time. With this application you can spread these files on specified basis. And also
because this app is multithreading this will work much faster than copy paste using basic OS features.
