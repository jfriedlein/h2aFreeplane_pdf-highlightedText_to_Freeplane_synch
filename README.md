# h2aFreeplane_pdf-highlightedText_to_Freeplane_synch
Freeplane script to organise highlighted text and notes from pdf files as Freeplane mindmap. This is very helpful to organise the information from many pdfs (usually spread and hidden in many annotations) as a mindmap, similar to Docear.

# Currently in testing state! This will probably take up entire 2024 to be certain h2aFreeplane is reliable. When testing is done and the implementation is cleaned and documented, an official "Release" will be available.
# Testing status (14.02.2024): ~240 pdfs, ~7000 annotations
# Testing status (14.06.2024): ~500 pdfs, ~11000 annotations
# Testing status (08.11.2024): ~670 pdfs, ~15000 annotations

## What it does
Starting from pdf-files of publications, books, etc. with highlighted text and notes, the Freeplane groovy script, with help of Python executables, extracts highlighted texts and notes from the pdf and enters it as nodes into Freeplane. The nodes in Freeplane can automatically be synchronised with the PDF, so all changes done in Freeplane to the annotations are written back into the PDF and vice versa. This is similar to the PDF handling capabilities of [Docear](https://www.youtube.com/watch?v=yDAfcSHxjbM).

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/h2aFreeplane%20-%20overview.png" width=75% height=75%>

## Why use h2aFreeplane? Why organise pdf highlights and annotations as mindmap?
Whether h2aFreeplane is an asset to your toolbox depends on your working style. I tend to read many publications as pdf, thereby highlighting text that is important or contains key phrases or valuable information, and adding pop-up notes with ideas/questions/todos. Thereby, one collects a vast amount of information, which is however spread over hundreds of pdfs. If you remember having read something, the chances are high you won't find it again. Even searching the internet might let you down as it does not know what pdfs you have read and sometimes the pdf's underlying text content is not searchable or quite messed up.
Here mindmaps come into play as they are able to collect a vast amount of information, make the data groupable, let you collapse/hide parts, and are fully searchable (searching extracted pdf annotations is much faster than searching the entire text content of pdfs). However, if you collect the data manually, it is a lot of work and almost impossible to maintain/synchronise the mindmap and the pdfs.
h2aFreeplane resolves all of these issues.

[Freeplane How-To](https://www.oldergeeks.com/downloads/files/freeplane-handbook-fullcircle-parts-1-15.pdf)

## Installation and setup
1. Install the mindmapping software Freeplane (https://docs.freeplane.org/) (h2aFreeplane tested for 1.10.4 and 1.12.5 on Linux and xxx on Windows)
2. Download the h2aFreeplane package (at the moment download this repository), unpack the folder somewhere on your hard drive, e.g. "~/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch"
3. Adapt the Freeplane preferences under Tools->Preferences to allow automatic script execution (read and write permission are needed to create the tmp-files that transfer the information from Freeplane to the h2a-Python-executables and back, execution of external programs is needed to execute the h2a-Python-executables and start the pdf-viewer, it might be necessary to allow network operations if some file are located e.g. on a USB stick)

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Freeplane%20settings%20for%20script%20execution.png" width=50% height=50%>

4. In the Freeplane preferences add the path to "~/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch" to the "Script search path", see figure above
5. Restart Freeplane to let it load the scripts.
6. After the restart, you should see both scripts under Tools->Scripts

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Freeplane%20with%20successfully%20loaded%20h2aFreeplane%20scripts.png" width=50% height=50%>

7. You can assign hotkeys to the execution of both scripts. I prefer "alt+h" for exeuction of "H2a Freeplane", and "alt+p" for execution of "H2a Open Pdf on Annot Page"
8. To hide the attributes and attribute symbols for the annotation nodes: View->Node attributes->"Hide all attributes" and turn off "Show icon for attributes"

## Usage
Exemplary pdf with highlighted text:

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf.png" width=50% height=50%>

1. Drag&Drop (holding ctrl+shift to get a link to the pdf, not a file copy) a pdf file into your mindmap.
2. Execute the script "H2a Freeplane" (e.g. by hotkey "alt+h"). Running the script for the first time might take several seconds, because it appears to be compiled. When you run a script for the first time, a warning may appear as script execution can be a security concern. If in question, look through the code or execute it partially.
3. Now the mindmap should list all annotations as child nodes to the pdf-file and the content field of each annotation in the pdf should contain the extracted content.

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20with%20loaded%20annotations.png" width=50% height=50%>
<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20after%20first%20h2a.png" width=50% height=50%>

4. Now you can freely modify the annotation content and afterwards execute the script "H2a Freeplane" (e.g. by hotkey "alt+h") again to write your changes back into the pdf to synchronise both.
<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20with%20loaded%20annotations%20after%20some%20manual%20cleaning.png" width=50% height=50%>

5. If you select one of the annotation nodes and execute the script "H2a Open Pdf on Annot Page" (e.g. by "alt+p" or clicking the button symbol at the front of an annotation node which needs to be selected), you can open the pdf directly jumping to the page where the selected annotation is located. Note that you will have to enter the paths to your desired pdf-viewer in the "h2aOpenPdfOnAnnotPage.groovy" script to be able to use it. By default, the script uses the default pdf viewer set on your PC to make sure the pdf can be opened. However, this default option does not support jumping to the annotation page. To enable this feature you can choose your desired pdf viewer and the paths and commands inside the h2aOpenPdfOnAnnotPage.groovy script. Please note that PDF viewers might protect the pdf file and block changes to it. Therefore, it might be necessary to close the pdf in the PDF viewer and then run h2a. Preferably use a PDF viewer without this "feature".

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20PDF%20after%20h2a%20and%20some%20manual%20cleaning.png" width=50% height=50%>

6. You can continue to add annotations in the pdf, delete them, change existing annotations, etc. and use the script "H2a Freeplane" to synchronise Freeplane and the PDF.

7. After running h2aFreeplane on a pdf, the keyword "h2a" is added to the "keywords" field of the pdf metadata. This can be displayed e.g. in a file explorer to already see there whether a file has already been processed by h2aFreeplane. For windows file explorer [PDF Property Extension](https://coolsoft.altervista.org/en/pdfpropertyextension#technicaldetails) can enable showing pdf metadata. For Linux ...?

8. In case of any problems, error messages should pop-up. In case of issues please create an "Issue" here in GitHub. Moreover, the Freeplane log files (open via Tools->Open user directory->logs->log.0) also give some information on the process of the script.

## Software scheme
Freeplane -> groovy script as interface -> python executables processing the pdf

todo add image, etc.

## Features
- Extracts highlighted text from a pdf and enters the extracted text into the content of the annotation (using the h2a-engine, see https://github.com/jfriedlein/h2a_pdf-highlightedText_to_annotation for details)
- Loads the content of annotations into Freeplane
- Enables making changes to the annotation content in Freeplane and writing these changes back into the original PDF to keep the PDF and Freeplane synchronised
- Deleting an annotation in the PDF will delete the annotation node in Freeplane
- Deleting an annotation node in Freeplane will not delete the annotation in the pdf (by design, could be changed if desired), but stops h2aFreeplane from again importing the annotation (will not appear in Freeplane again). To undo such a partly deleted annotation, remove the line that belongs to this annotation from the note of the parent pdf node, this will make h2aFreeplane import the annotation again.
- You can rearrange the annotations, group them, add annotations nodes as children to annotations nodes and add non-annotation nodes (standard Freeplane nodes). Currently only three levels of children are supported (can be extended in the groovy script).
- Optional: Freshly added annotation nodes are coloured in green, when they are inserted. During the next execution of h2a, the colour will be reverted to the default text colour (black).
- Optional: Freshly added annotations are sorted by page number. Therefore, a new annotation is placed before the first already existing annotation with a higher page number than the new one.

## Python executables
Reading and writing of the pdf annotation is based on Python (fitz, pymupdf). However, due to pre-built executables your PC does not need to have Python or any module installed to be able to run h2aFreeplane. Currently the Python-executables are only pre-built for Windows and Linux, but it should easily be possible to build them e.g. for Mac (using auto-py-to-exe). In case there are some security considerations or your antivirus software does not like the Python executables, you can also easily build the Python executables by yourself (using Python3, fitz, auto-py-to-exe and the Python source code from https://github.com/jfriedlein/h2a_pdf-highlightedText_to_annotation building the files h2aFreeplane_caller.py, h2a_update_from_Freeplane_caller.py both with the folder h2a_functions).

## Docear
Docear (https://docear.org/) is a fantastic system, which might has been born to early to truly thrive. Unfortunately, it nowadays appears outdated, unsupported, and somewhat buggy/error-prone (as of 2024). h2aFreeplane opts to revive the pdf handling capabilities of Docear bringing it into the current decade and trying to avoid some of its original shortcomings. We try to achieve this by:
- Limiting this only to the handling of pdf (no JabRef, etc.) to keep it standalone and less complex, more like a module (one piece of the large Docear puzzle)
- Implementing this as a mere script to Freeplane, so it is easier to keep up with new Freeplane developments and versions, and to avoid maintaining an own full-blown software.
- Separating: Freeplane GUI usage - Freeplane script interface - reading/writing pdf annotations
- Not synchronising every modification done in Freeplane just-in-time with a node change, but only synchronising when the user executes the script "H2a Freeplane"
- Using a temporary text file to transfer the annotation content from the Python executable reading the PDF annotation to Freeplane, and a separate file to write the changes from Freeplane into the PDF. This simplifies the execution and debugging as the files can be read by any user and contain not just a single just-in-time change.
- Using Python to do the PDF annotation handling, which provides powerful, fast, and up to date packages, which are actively developed and extended.
- Being usable with any PDF viewer (that creates proper annotations)

## ToDo
- Annotation nodes need to stay as children (or grandchildren, ...) of the parent pdf-node, because only the parent pdf-node contains the link to the PDF as node link. So you cannot move an annotation node somewhere completely else, which was possible in Docear. The former approach is beneficial in case the path to the pdf changes, then we only need to change the path once in the parent node.
- When using the button in front of an annotation node to open the pdf on the corresponding page, you need to click on the node first to select it. The script does not know which node started it based on the button, but only based on the currently selected node.
- When hovering over the nodes the attributes and notes pop-up. This can be distracting, but is a Freeplane settings. Can this be deactivated?
- Currently hardcoded to timezone Europe/Berlin
- Check different timezone formats such as SumatraPDF "...Z" instead of "+1'00"
- Currently only file ending ".pdf" is detected, not ".PDF", maybe use ".toLowerCase()" in all those places


