# h2aFreeplane_pdf-highlightedText_to_Freeplane_synch
Freeplane script to organise highlighted text and notes from pdf files as Freeplane mindmap. This is very helpful to organise the information from many pdfs (usually spread and hidden in many annotations) as a mindmap, similar to Docear. Available as Freeplane addon (see releases) or Freeplane scripts.


## What it does
Starting from pdf-files of publications, books, etc. with highlighted text and notes, the Freeplane groovy script, with help of Python executables, extracts highlighted texts and notes from the pdf and enters it as nodes into Freeplane. The nodes in Freeplane can automatically be synchronised with the PDF, so all changes done in Freeplane to the annotations are written back into the PDF and vice versa. This is similar to the PDF handling capabilities of [Docear](https://www.youtube.com/watch?v=yDAfcSHxjbM).

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/h2aFreeplane%20-%20overview.png" width=75% height=75%>

## Why use h2aFreeplane? Why organise pdf highlights and annotations as mindmap?
Whether h2aFreeplane is an asset to your toolbox depends on your working style. I tend to read many publications as pdf, thereby highlighting text that is important or contains key phrases or valuable information, and adding pop-up notes with ideas/questions/todos. Thereby, one collects a vast amount of information, which is however spread over hundreds of pdfs. If you remember having read something, the chances are high you won't find it again. Even searching the internet might let you down as it does not know what pdfs you have read and sometimes the pdf's underlying text content is not searchable or quite messed up.
Here mindmaps come into play as they are able to collect a vast amount of information, make the data groupable, let you collapse/hide parts, and are fully searchable (searching extracted pdf annotations is much faster than searching the entire text content of pdfs). However, if you collect the data manually, it is a lot of work and almost impossible to maintain/synchronise the mindmap and the pdfs.
h2aFreeplane resolves all of these issues.

[Freeplane How-To](https://www.oldergeeks.com/downloads/files/freeplane-handbook-fullcircle-parts-1-15.pdf)


## Installation and setup
### Using Freeplane addon "h2aFreeplane-vx.x.addon.mm"
1. Install the mindmapping software Freeplane (https://docs.freeplane.org/) (ideally most recent version 1.12.8 or newer (tested for Linux 1.12.5 and Windows 1.12.8), for older versions see [section below](#using-freeplane-scripts))
2. Download the Freeplane addon "h2aFreeplane-vx.x.addon.mm" (see releases in column on the right) and save it somewhere on your PC
3. Install the addon (Procedure: [Freeplane Addons](https://docs.freeplane.org/getting-started/Add-ons_(install).html))
    - In Freeplane, select Tools -> Add-ons -> Search and install -> Install add-on from a known location -> Search: select the file "h2aFreeplane-v2.0.addon.mm" from where you saved it
    - Click on "Install" (it might take 30 seconds until any message box pops up, because the prebuilt Python executables are about 100 MB large and need to be unpacked)
    - If the installation was successful, restart Freeplane. If the installation failed, see [section below](#using-freeplane-scripts))
4. After successful installation and restart (might be necessary to restart twice to resolve "Permission denied" error message), you should see Tools -> h2aFreeplane -> h2aFreeplane
5. You can assign hotkeys to the execution of both scripts. I prefer "alt+h" for execution of "H2a Freeplane", and "alt+p" for execution of "H2a Open Pdf on Annot Page"
6. To hide the attributes and attribute symbols for the annotation nodes: View->Node attributes->"Hide all attributes" and turn off "Show icon for attributes"

Note: What does "h2aFreeplane-v2.0.addon.mm" install, where, and why
- Addons are installed to the user directory (can be accessed in Freeplane -> Tools -> Open user directory)
- In the folder "addons", you find "h2aFreeplane" with subfolder "scripts".
    - Therein "h2aFreeplane.groovy" is the main script that creates the mindmap nodes, calls the Python executables, etc.
    - The folders "h2aFreeplane_Python-...-executables" contain the prebuilt Python executables "h2aFreeplane_caller" and "h2a_update_from_Freeplane_caller" (with needed packages) for Windows and Linux
- In the folder "lib" (not the subfolder, but the main folder on the same level as folder "addons"), the utility script "H2A_utilityScripts.groovy" is located. It contains common function that are used in "h2aFreeplane.groovy" and "h2aOpenPdfOnAnnotPage.groovy"
    - It is located in the default script classpath for "lib".
- In the folder "scripts", "h2aOpenPdfOnAnnotPage.groovy" is located, which enables to open the pdf from an annotation node in the mindmap and directly jump to the page that contains this annotation
    - It is located in the default script search path to automatically add it to Tools -> Scripts -> "H2A Open Pdf On Annot page". Thereby it can be run from "child.link.text" via "menuitem" to start it by clicking the gray box in front of each annotation node.
- Uninstalling the addon fails to remove the "addons/h2aFreeplane" folder, so manually delete it if needed

### Using Freeplane scripts
In case the addon installation does not work, you want to install the addon files in a central location, or some other reason:
1. Install the mindmapping software Freeplane (the Freeplane versions should be less problematic here, also tested for 1.10.4, 1.12.5)
2. Download the h2aFreeplane package (this repository), unpack the folder somewhere on your hard drive, e.g. "~/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch"
3. Adapt the Freeplane preferences under Tools -> Preferences to allow automatic script execution (read and write permission are needed to create the tmp-files that transfer the information from Freeplane to the h2a-Python-executables and back, execution of external programs is needed to execute the h2a-Python-executables and start the pdf-viewer, it might be necessary to allow network operations if some file are located e.g. on a USB stick)
4. In the Freeplane preferences add the path to "/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/scripts" (for "h2aFreeplane.groovy") and "/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/zips/scripts" (for "h2aOpenPdfOnAnnotPage.groovy") to the "Script search path"
5. Open the file "h2aFreeplane.groovy" located in "~/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/scripts". Set the variable "path_detour_for_local_tests" to "../zips/addons/h2aFreeplane/scripts/". Save the script and close it.
6. Add the path to "H2A_utilityScripts.groovy" located in the subdirectory "zips/lib" to "Script classpath", e.g. "~/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/zips/lib"
7. Restart Freeplane to let it load the scripts.
8. After the restart, you should see both scripts under Tools->Scripts

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Freeplane%20with%20successfully%20loaded%20h2aFreeplane%20scripts.png" width=50% height=50%>

9. You can assign hotkeys to the execution of both scripts. I prefer "alt+h" for execution of "H2a Freeplane", and "alt+p" for execution of "H2a Open Pdf on Annot Page"
10. To hide the attributes and attribute symbols for the annotation nodes: View->Node attributes->"Hide all attributes" and turn off "Show icon for attributes"


## Usage
Exemplary pdf with highlighted text:

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf.png" width=50% height=50%>

1. Drag&Drop (holding ctrl+shift to get a link to the pdf, not a file copy) or copy-paste a pdf file into your mindmap.
2. Execute the script "H2a Freeplane" (e.g. by hotkey "alt+h"). Running the script for the first time might take several seconds, because it appears to be compiled. When you run a script for the first time, a warning may appear as script execution can be a security concern. If in question, look through the code or execute it partially.
3. Now the mindmap should list all annotations as child nodes to the pdf-file and the content field of each annotation in the pdf should contain the extracted content. (In v2.0 freshly added annotations are coloured green until they are not new anymore. This option and the colour can be configured in "h2aFreeplane.groovy".)

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20with%20loaded%20annotations.png" width=50% height=50%>
<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20after%20first%20h2a.png" width=50% height=50%>

4. Now you can freely modify the annotation content and afterwards execute the script "H2a Freeplane" (e.g. by hotkey "alt+h") again to write your changes back into the pdf to synchronise both.
<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20pdf%20with%20loaded%20annotations%20after%20some%20manual%20cleaning.png" width=50% height=50%>

5. If you select one of the annotation nodes and execute the script "H2a Open Pdf on Annot Page" (e.g. by "alt+p" or clicking the button symbol at the front of an annotation node which needs to be selected), you can open the pdf directly jumping to the page where the selected annotation is located.
    - Note that you will have to enter the paths to your desired pdf-viewer in the "h2aOpenPdfOnAnnotPage.groovy" script to be able to use it. By default, the script uses the default pdf viewer set on your PC to make sure the pdf can be opened. However, this default option does not support jumping to the annotation page. To enable this feature you can choose your desired pdf viewer and the paths and commands inside the h2aOpenPdfOnAnnotPage.groovy script. Please note that PDF viewers might protect the pdf file and block changes to it. Therefore, it might be necessary to close the pdf in the PDF viewer and then run h2a. Preferably use a PDF viewer without this "feature".
    - As this is a script, you will need to allow the execution of scripts in Freeplane -> Tools -> Preferences -> Plugins
    - As this script starts a PDF viewer, you will need to allow the execution of external programs in Freeplane -> Tools -> Preferences -> Plugins

<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/Exemplary%20PDF%20after%20h2a%20and%20some%20manual%20cleaning.png" width=50% height=50%>

6. You can continue to add annotations in the pdf, delete them, change existing annotations, etc. and use the script "H2a Freeplane" to synchronise Freeplane and the PDF.

7. After running h2aFreeplane on a pdf, the keyword "h2a" is added to the "keywords" field of the pdf metadata. This can be displayed e.g. in a file explorer to already see there whether a file has already been processed by h2aFreeplane. For windows file explorer [PDF Property Extension](https://coolsoft.altervista.org/en/pdfpropertyextension#technicaldetails) can enable showing pdf metadata. For Linux ...?

8. In case of any problems, error messages should pop-up. In case of issues please create an "Issue" here in GitHub. Moreover, the Freeplane log files (open via Tools->Open user directory->logs->log.0) also give some information on the process of the script.


## Features
- Extracts highlighted text from a pdf and enters the extracted text into the content of the annotation (using the h2a-engine, see https://github.com/jfriedlein/h2a_pdf-highlightedText_to_annotation for details)
- Loads the content of annotations into Freeplane
- Enables making changes to the annotation content in Freeplane and writing these changes back into the original PDF to keep the PDF and Freeplane synchronised
- Deleting an annotation in the PDF will delete the annotation node in Freeplane
- Deleting an annotation node in Freeplane will not delete the annotation in the pdf (by design, could be changed if desired), but stops h2aFreeplane from again importing the annotation (will not appear in Freeplane again). To undo such a partly deleted annotation, remove the line that belongs to this annotation from the note of the parent pdf node, this will make h2aFreeplane import the annotation again.
- You can rearrange the annotations, group them, add annotations nodes as children to annotations nodes and add non-annotation nodes (standard Freeplane nodes). Currently only three levels of children are supported (can be extended in the groovy script).
- Customisable user-settings: ([below](#how-to-customise-h2aFreeplane-by-user-settings))
    - Optional (on by default): Freshly added annotation nodes are coloured in green, when they are inserted. During the next execution of h2a, the colour will be reverted to the default text colour (black).
    - Optional (on by default): Freshly added annotations are sorted by page number. Therefore, a new annotation is placed before the first already existing annotation with a higher page number than the new one.
    - Optional (on by default): The annotation colour chosen in the pdf is copied to Freeplane. Hence, if you colour a text highlight or a note for instance red in the pdf, the corresponding annotation node in Freeplane will receive the same colour. The colours are also synchronised, such that colour changes in Freeplane also transfer to the PDF. Moreover, as most pdf viewers use a default color, often similar to yellow, for highlighting, we offer the option to ignore colours that are close (with adjustable tolerance) to a user-defined colour (see "annotColour_to_be_ignored" [below](#how-to-customise-h2aFreeplane-by-user-settings) ).

## Customise
- Freeplane -> Tools -> Open user directory
- Go to subfolder: addons/h2aFreeplane/scripts
    - Edit file "h2aFreeplane.groovy": sort_newly_added_annotationNodes_by_page, color_newly_added_annotationNodes, colour_node_in_annotColour, add_backup_filepath_attribute, ...
- Go to subfolder: scripts
    - Edit file "h2aOpenPdfOnAnnotPage.groovy": pdf_viewer_Linux, pdf_viewer_Windows, path_to_lit_folder_x, ...

## Software scheme
<img src="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch/blob/main/docu/h2aFreeplane-softwareStructure%20-%20V0.png" width=100% height=100%>

The h2a-engine is available as separate repository [here](https://github.com/jfriedlein/h2a_pdf-highlightedText_to_annotation).

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

## How to update the h2aFreeplane addon to a new version
- Start Freeplane -> Tools -> Add-ons -> Uninstall "h2aFreeplane x.x"
- Restart Freeplane -> Tools -> Open user directory -> manually delete the leftover subfolder "addons/h2aFreeplane" (for some reason not done automatically)
- Download the new versions of h2aFreeplane and again follow the steps under [Installation and setup](#installation-and-setup))
- Remember to again make custom changes to the installed groovy files, such as setting your preferred pdf-viewer, switching optional features on/off, updating custom paths, ...

## How to customise h2aFreeplane by user-settings
- Since v2.2, you find the file "H2A_userSettings_DEFAULT.groovy" in the Freeplane user directory in the subfolder "lib".
- When you change user-settings for the first time:
    - Rename the file to "H2A_userSettings.groovy", and edit its content by renaming the class "H2A_userSettings_DEFAULT" to "H2A_userSettings".
    - Then you can modify the user settings. After changing the user-settings, you need to restart Freeplane to let it reload the lib-file.
    - The renamed file "H2A_userSettings.groovy" will be kept also after uninstalling and reinstalling (updating) h2aFreeplane. Therefore, you custom user-settings are kept for all future updates.
- When you change user-settings and already did the initial steps of the previous bullet point
    - Change the user settings
    - Manually insert parameters from the "H2A_userSettings_DEFAULT.groovy" file, that might have been added in a future release.

## ToDo
- Annotation nodes need to stay as children (or grandchildren, ...) of the parent pdf-node, because only the parent pdf-node contains the link to the PDF as node link. So you cannot move an annotation node somewhere completely else, which was possible in Docear. The former approach is beneficial in case the path to the pdf changes, then we only need to change the path once in the parent node.
- When using the button in front of an annotation node to open the pdf on the corresponding page, you need to click on the node first to select it. The script does not know which node started it based on the button, but only based on the currently selected node.
- When hovering over the nodes the attributes and notes pop-up. This can be distracting, but is a Freeplane settings. Can this be deactivated?
- Currently hardcoded to timezone Europe/Berlin
- Check different timezone formats such as SumatraPDF "...Z" instead of "+1'00"
- Add a separate file with custom-parameters to keep those values when updating to a new version
- Currently only file ending ".pdf" is detected, not ".PDF", maybe use ".toLowerCase()" in all those places
- Microsoft Edge: When changing the annotation text of an existing annotation, Edge does not change the modification time of the annotation, hence h2a cannot detect this change. Is this an Edge bug/feature?

## Testing status (27.02.2025): 820 pdfs, 18646 annotations

