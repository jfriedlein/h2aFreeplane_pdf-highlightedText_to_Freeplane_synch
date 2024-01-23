# h2aFreeplane_pdf-highlightedText_to_Freeplane_synch
Freeplane script to organise highlighted text and notes from pdf files as Freeplane mindmap. This is very helpful to organise the information from many pdfs (usually spread and hidden in many annotations) as a mindmap, similar to Docear.

# Currently in testing state! This will probably take up entire 2024 to be certain h2aFreeplane is reliable. When testing is done an official "Release" will be available and some ringing the bell might be done.

## What it does
Starting from pdf-files of publications, books, etc. with highlighted text and notes, the Freeplane groovy script with help of Python executables extracts the highlighted text and notes from the pdf and enters it as nodes into Freeplane. The nodes in Freeplane can automatically be synchronised with the PDF, so all changes done in Freeplane to the annotations are written back into the PDF and vice versa. This is similar to the PDF handling capabilities of Docear.

![h2a_scheme](https://github.com/jfriedlein/h2a_pdf-highlightedText_to_annotation/blob/main/guide/h2a_scheme.png)

## Installation and setup
1. Install the mindmapping software Freeplane
2. Download the h2aFreeplane package, unpack the folder somewhere on your hard drive, e.g. "~/h2aFreeplane"
3. Adapt the Freeplane preferences to allow automatic script execution, see "Freeplane settings for script execution.png" (read and write permission are needed to create the tmp-files that transfer the information from Freeplane to the h2a-Python-executable and back, execution external program is needed to execute the h2a-python-executables and start the pdf-viewer, it might be necessary to allow network operations if some file is e.g. on a USB stick)
4. Add the path to "~/h2aFreeplane" to the "Script search path", see figure "Freeplane settings for script execution.png"
5. Restart Freeplane.
6. After the restart, you should see both script under Tools->Scripts, see figure "Freeplane with succesfully loaded h2aFreeplane scripts.png"
7. You can assign hotkeys to the execution of both scripts. I prefer "alt+h" for exeuction of "H2a Freeplane", and "alt+p" for execution of "H2a Open Pdf on Annot Page"

## Usage
1. Drag&Drop (holding ctrl+shift to get a link not a file copy) a pdf file into your mindmap.
2. Execute the script "H2a Freeplane" (e.g. by hotkey "alt+h"). Running the script for the first time might take several seconds, because it appears to be compiled.
3. Now the mindmap should list all annotations as child nodes to the pdf-file
4. If you select one of the annotation nodes and execute the script "H2a Open Pdf on Annot Page" (e.g. by "alt+p" or clicking the button symbol at the front of an annotation node) to open the pdf directly jumping to the page where the selected annotation is located. Note that you will have to enter the paths to your desired pdf-viewer in the "h2aOpenPdfOnAnnotPage.groovy" script to be able to use it.
5. In case of any problems, error messages should pop-up. Moreover, the Freeplane log files (open via Tools->Open user directory->logs->log.0) also give some information on the process of the script.

## Software scheme
Freeplane -> groovy script as interface -> python executables processing the pdf

## Features
- Extracts highlighted text from a pdf and enters the extracted text into the content of the annotation (using the h2a-engine, see h2a-repo for details)
- Loads the content of annotations into Freeplane
- Enables making changes to the annotation content in Freeplane and writing these changes back into the original PDF to keep the PDF and Freeplane synchronised
- Deleting an annotation in the PDF will delete the annotation node in Freeplane.
- Deleting an annotation node in Freeplane will not delete the annotation in the pdf (by design, could be changed if desired), but stops h2aFreeplane from again importing the annotation (will not appear in Freeplane again). To undo such a partly deleted annotation, remove the line that belongs to this annotation from the notes of the parent pdf node, this will make h2aFreeplane import the annotation again.
- You can rearrange the annotations, group them, add annotations nodes as children to annotations nodes and add non-annoation nodes (standard Freeplane nodes). Currently only three levels of children are supported (can be extended in the groovy script).

## Python executables
Reading and writing of the pdf annotation is based on Python (fitz, pymupdf). However, due to prebuild executables the PC does not need to have Python or any module installed to be able to run h2aFreeplane. Currently the Python-executables are only prebuilt for Windows and Linux, but it should easily be possible to build them e.g. for Mac (using auto-py-to-exe). In case there are some security considerations or your antivirus software does not like the Python executables, you can also easily build the Python executables by yourself (using Python3, fitz, auto-py-to-exe).

## Docear
Docear is a fantastic system, which might has been born to early to truly strive. Unfortunately, it nowadays appears outdated, unsupported, and somewhat buggy/fehleranfällig (as of 2024). h2aFreeplane opts to revive the pdf handling capabilities of Docear bringing it into the current decade and trying to avoid some of its original shortcomings. We try to achieve this by:
- Limiting this only to the handling of pdf (no JabRef, etc.) to keep it standalone and less complex, more like a module (one piece of the large Docear puzzle)
- Implementing this as a mere script to Freeplane, so it is easier to keep up with new Freeplane developments and versions, and to avoid maintaining an own full-blown software.
- Separating: Freeplane GUI usage - Freeplane script interface - reading/writing pdf annotations
- Not synchronising every modification done in Freeplane just-in-time with a node change, but only synchronising when the user executes h2a
- Using a temporary text file to transfer the annotation content from the Python executable reading the PDF annotation to Freeplane, and a separate file to write the changes from Freeplane into the PDF. This simplifies the execution and debugging as the files can be read by any user and contain not just a single just-in-time change.
- Using Python to do the PDF annotation handling, which provides powerful, fast, and up to date packages, which are actively developed and extended.

## ToDo
- Annotation nodes need to stay as children (or grandchildren, ...) of the parent pdf-node, because only the parent pdf-node contains the link to the PDF as node link. So you cannot move an annotation node somewhere completely else, which was possible in Docear. The former is beneficial in case the path to the pdf changes, then we only need to change the path once in the parent node.
- When using the button in front of an annotation node to open the pdf on the corresponding page, you need to click on the node first to select it. The script does not know which node started it based on the button, but only based on the currently selected node.
- Currently hardcoded to timezone Europe/Berlin
- Check different timezone formats such as SumatraPDF "...Z" instead of "+1'00"
