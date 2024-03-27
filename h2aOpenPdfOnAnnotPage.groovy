// @ExecutionModes({ON_SINGLE_NODE})
// "ON_SELECTED_NODE": In case we select nodes from different pdf branches, it will open multiple pdfs
// "ON_SINGLE_NODE": Choose this if you want to open only the pdf of one node

// @note For Windows the paths to the pdf-viewer executables can contain blank spaces but all backslashes "\" need to be replaced by slashes "/" for the paths to work in Java

// USER-Parameters
pdf_viewer_Linux = "default"
pdf_viewer_Windows = "default"

// Determine the operating system to choose the Windows or Linux built of the Python-executables
// [https://stackoverflow.com/questions/4689240/detecting-the-platform-window-or-linux-by-groovy-grails]
operatingSystem_tmp = System.properties['os.name'].toLowerCase()
if ( operatingSystem_tmp.contains('windows') )
{
    operatingSystem = "Windows"
}
else if ( operatingSystem_tmp.contains('linux') )
{
    operatingSystem = "Linux"
}
else
{
    ui.errorMessage("h2aOpenPdfOnAnnotPage<< Cannot determine operating system="+operatingSystem_tmp+" as 'Windows' or 'Linux'. Currently only Windows and Linux are supported.")
}


def findFirstParentWithPdfLink( node_selected )
{
	   // Find the first parent generation that contains a link to a PDF
	   if ( node_selected.link.text && node_selected.link.text.contains(".pdf") )
	   {
		return node_selected
	   }
	   else if ( node_selected.parent.link.text && node_selected.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent
	   }
	   else if ( node_selected.parent.parent.link.text && node_selected.parent.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent.parent
	   }
	   else if ( node_selected.parent.parent.parent.link.text && node_selected.parent.parent.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent.parent.parent
	   }
	   else
	   {
		//ui.errorMessage("h2aFreeplane<< Cannot find node/parent/grandparent/grand-grandparent of node ("+node_selected.text+") that contains link to PDF")
		return null
   	   }
}


try
{
    c.statusInfo = 'h2aOpenPdfOnAnnotPage<< Opening pdf ...' 

	// Find the first parent of the selected node "node" that contains a link to a PDF (searches three generations up)
	// @todo Of course the risks exists that we find the wrong node, if any other node contains a link to a PDF
	 node_with_pdf = findFirstParentWithPdfLink( node )
	// If no node_with_pdf was found, print error message and end the script
	 if ( !node_with_pdf )
	 {
		ui.errorMessage("openPdfOnAnnotPage<< Cannot find node/parent/grandparent/grand-grandparent of node ("
                		+ node.text + ") that contains link to PDF.")
		return false
	 }

    // Extract path to the PDF from the node_with_pdf node
     path_to_pdf = node_with_pdf.link.file.toString().replace("%20"," ")
	
	// Set the page number on which the PDF shall be opened
	 if ( node["annot_page"] )
	 {
	    // Extract the page number for the current annotation from its attribute
		 annot_page = node["annot_page"]
	 }
    // If the annot page is empty, e.g. when opening the parent node, we open the PDF on the first page 1
	 else
	 {
		annot_page = 1
	 }

    // Open the pdf with pdf-viewer:
     // ProcessBuilder lets us start the pdf-viewer without waiting for it to close, so Freeplane is not frozen, but can be continuously used.
     // @note Under Linux the "(... &)" around the command might be needed to keep the pdf-viewer open even when Freeplane is closed, else processes opened by this script would automatically be closed and changes done in the pdf-viewer would be lost (no pdf-viewer message "do you want to save the changes") when Freeplane is closed.
    if ( operatingSystem=="Linux" )
    {
        switch ( pdf_viewer_Linux )
        {
            // ["bash","-c",...] ensures that path_to_pdf with blankspaces are interpreted correctly [https://stackoverflow.com/questions/786160/groovy-execute-with-parameters-containing-spaces]
            case "okular":
                // Ubuntu Okular
                //  Okular: "-p" = open PDF on certain page
                //          "--unique" = do not reopen the PDF if already open, but only change the page number
                // @todo unique will allow only one pdf to be open, clearly that is not desired, but only one instance of each pdf should open but still multiple different pdfs in separate windows or tabs
                Process process = new ProcessBuilder()
                                      .command(["bash","-c","( okular --unique -p "+annot_page +' "' + path_to_pdf + '" &)'])
                                      .start()
                break
            case "evince":
                // Ubuntu evince "document viewer"
                //  Command line options [https://help.gnome.org/users/evince/stable/commandline.html.en]
                Process process = new ProcessBuilder()
                                      .command(["bash","-c","evince --page-label="+annot_page +' "' + path_to_pdf + '"'])
                                      .start()
                break
            case "chrome":
                // Google chrome
                // @todo Currently does not work, because "#page=" for the page number is wrongly escaped
                ui.errorMessage("h2aOpenPdfOnAnnotPage<< Chrome is currently not supported")
                //Process process = new ProcessBuilder()
                //                  .command(["bash","-c","google-chrome"+' "' + path_to_pdf + '"'+'#page='+annot_page ])
                //                  .start()
                break
            case "default": // [https://www.baeldung.com/linux/pdf-open-command-line]
                Process process = new ProcessBuilder()
                                      .command(["bash","-c","xdg-open" +' "' + path_to_pdf + '"'])
                                      .start()
                break
            default:
                ui.errorMessage("h2aOpenPdfOnAnnotPage<< Selected pdf_viewer="+pdf_viewer_Linux+" not available.")
        }
    }
    else if ( operatingSystem=="Windows" )
    {
        switch ( pdf_viewer_Windows )
        {
            case "adobe": // [https://stackoverflow.com/questions/619158/adobe-reader-command-line-reference]
                path_to_adobe = "C:/Program Files/Adobe/Acrobat DC/Acrobat/Acrobat.exe"
                Process process = new ProcessBuilder()
                                      .command("\""+path_to_adobe+"\" /A \"page="+annot_page+"\" \""+path_to_pdf.replace("\\","/")+"\"")
                                      .start()
                break
            case "okular": // []
		        path_to_okular = "C:/Program Files/WindowsApps/KDEe.V.Okular_23.801.1522.0_x64__7vt06qxq7ptv8/bin/okular.exe"
                Process process = new ProcessBuilder()
                                      .command("\""+path_to_okular+"\" -p "+annot_page+" \""+path_to_pdf.replace("\\","/")+"\"")
                                      .start()
                break
            case "pdfXchange": // [https://downloads.pdf-xchange.com/PDFVManual.pdf]
                path_to_pdfXchange = "C:/Program Files (x86)/Tracker Software/PDF Viewer/PDFXCview.exe"
                Process process = new ProcessBuilder()
                                      .command("\""+path_to_pdfXchange +"\" /A \"page="+annot_page+"\" \""+path_to_pdf.replace("\\","/")+"\"")
                                      .start()
                break
            case "sumatra": // [https://www.sumatrapdfreader.org/docs/Command-line-arguments]
                path_to_sumatra = "C:/Program Files (x86)/SumatraPDF/SumatraPDF.exe"
                Process process = new ProcessBuilder()
                                      .command("\""+path_to_sumatra +"\" -page "+annot_page+" \""+path_to_pdf.replace("\\","/")+"\"")
                                      .start()
                break
            case "chrome":
                // Google chrome
                // @todo Currently does not work, because "#page=" for the page number is wrongly escaped
                ui.errorMessage("h2aOpenPdfOnAnnotPage<< Chrome is currently not supported")
                //Process process = new ProcessBuilder()
                //                  .command(["cmd","/c","google-chrome"+" \"" + path_to_pdf.replace("\\","/") + "\""+"#page="+annot_page+"\"" ])
                //                  .start()
                break
            case "default": // use default pdf-viewer (does not support jumping to annotation page in pdf)
                Process process = new ProcessBuilder()
                                      .command(["cmd","/c","\""+path_to_pdf.replace("\\","/")+"\""])
                                      .start()
                break
            default:
                ui.errorMessage("h2aOpenPdfOnAnnotPage<< Selected pdf_viewer="+pdf_viewer_Windows+" not available.")
        }
    }
	c.statusInfo = 'h2aOpenPdfOnAnnotPage<< Finished opening pdf.' 
}
catch (Exception e)
{
    println e
	message_text =  'h2aOpenPdfOnAnnotPage<< ... failed to open pdf.' 
	println message_text
	c.statusInfo = message_text
	logger.severe(message_text, e)
	ui.errorMessage( message_text + ' : ' + e )
}
