// @ExecutionModes({ON_SELECTED_NODE})

// Each annotation in the PDF is assigned a node. Information on the annotation (page, type, ID, ...) are stored as attributes to the node.
//  - Use of attributes [https://docs.freeplane.org/api/org/freeplane/api/Attributes.html], [https://docs.freeplane.org/api/org/freeplane/api/NodeRO.html]
//  - Hide attributes in the mindmap [https://sourceforge.net/p/freeplane/discussion/758437/thread/3b5e7fad44/]

// @todo How to avoid attributes to pop-up when hovering over node?
// @todo add attribute to parent pdf node with option output_mode to e.g. select "update_auto" instead of default "update_new"

// Import colours for printing text node.text red when an error occurs
import java.awt.Color

// Get base-directory where this very file is located to correctly execute the commands for the h2a_caller and h2a_update_from_Freeplane Python-executables
// [https://stackoverflow.com/questions/1163093/how-do-you-get-the-path-of-the-running-script-in-groovy]
import groovy.transform.SourceURI
import java.nio.file.Path
import java.nio.file.Paths
@SourceURI
URI sourceUri
Path scriptLocation = Paths.get(sourceUri)
path_to_this_folder = scriptLocation.getParent().toString();

// First part of the paths to main literature folder that are different for Linux and Windows
// @note Without the leading "file:"
 path_to_lit_folder_Linux = ""
 path_to_lit_folder_Windows = ""

// Determine the operating system to choose the Windows or Linux built of the Python-executables
operatingSystem = H2A_utilityScripts.get_operatingSystem()

// USER-PARAMETERS:
// With the new folder structure for building the Freeplane addon, the following path detour enables to use the local h2aFreeplane.groovy with the local exe-file for testing (after setting the script search path in Freeplane->Preferences->Plugins)
// @note The local "H2A_utilityScripts.groovy" is not considered and must be loaded/replaced manually
//path_detour_for_local_tests = "../zips/addons/h2aFreeplane/scripts/"
path_detour_for_local_tests = ""

// Path to the python executable for the h2a_caller
 path_to_exe_folder = path_to_this_folder + File.separator + path_detour_for_local_tests + "h2aFreeplane_Python-"+operatingSystem+"-executables"+ File.separator
 path_to_h2a_caller =  path_to_exe_folder + "h2aFreeplane_caller"
// Path to the python executable for h2a_update_from_Freeplane_caller
 path_to_h2a_update_from_Freeplane = path_to_exe_folder + "h2a_update_from_Freeplane_caller"
// Directory to store the temporary output of the h2a-caller and this Freeplane script
// @todo The directories are hardcoded here and repeated in "h2a_highlightedText_to_annotation.py" (when changing, you need to change both)
 path_to_h2a_tmp_directory = path_to_this_folder
 filename_h2a_pdf_output = "h2a_pdf-output.tmp"
 filename_h2a_freeplane_changes = "h2a_freeplane-changes.tmp"

// ES=entry_separator: String used to separate entries that are grouped into a single string, e.g. an array ("annot_text","annot_ID_001") is stored as single string "annot text ;x; annot_ID_001"
// @todo ES is hardcoded here and repeated in "h2a_highlightedText_to_annotation.py" (when changing, you need to change both)
ES = " ;x; "
// String to replace line-breaks "\n" when grouping a multi-line string into a single line
// @todo line_break_replacer is hardcoded here and repeated in "h2a_highlightedText_to_annotation.py" (when changing, you need to change both)
line_break_replacer = ' ;xnx; '

error_phrase = ">ERROR>"

sort_newly_added_annotationNodes_by_page = true

color_newly_added_annotationNodes = true
// Color used to mark newly added annotations
 Color DARK_GREEN = new Color(0,102,0);


// Annotation nodes in Freeplane can use the colour of the pdf annotations. E.g. if you colour the annotation in the pdf red, the node in Freeplane will also be coloured red, and vice versa.
 colour_node_in_annotColour = true
// Opacity of above background colour (0: transparent, 1: opac)
 opacity_background_colour = 0.3
// Most pdf viewers use a default color, often similar to yellow ([1,1,0]), for highlighting, to avoid all nodes with default colour to appear yellowish in the mindmap, we offer the option to ignore colours that are close (with adjustable tolerance) to a user-defined colour.
 // Set rgb-values for annotations colour to be ignored. Use rgb-values (0...1). Deactive the annotColour_to_be_ignored by setting it to an empty list "[]".
  annotColour_to_be_ignored = [1,1,0]
 // A tolerance of 0, will only ignore exactly annotColour_to_be_ignored, whereas a tolerance of more than sqrt(3) will ignore every colour
  annotColour_to_be_ignored_tolerance = 0.25


// [thanks to perplexity.ai]
class ColorChecker
{
    static boolean checkIfColorAccept(List<Float> colorToBeChecked, List<Float> annotColour_to_be_ignored, float annotColour_to_be_ignored_tolerance)
    {
        if ( annotColour_to_be_ignored.size() == 0 )
        {
           return true
        }

        float distance = Math.sqrt(
            (colorToBeChecked[0] - annotColour_to_be_ignored[0])**2 + 
            (colorToBeChecked[1] - annotColour_to_be_ignored[1])**2 + 
            (colorToBeChecked[2] - annotColour_to_be_ignored[2])**2
        )
        // Accept the color only if it is far enough away from the to be ignored colour
        return distance > annotColour_to_be_ignored_tolerance
    }
}

// Choose a debugging level, that is active when use node scripts via println or in "log" files. 0=no debugging output, 1=first level of debugging outputs, 2=..., 99=all levels
 debugging = 0


message_text = 'h2aFreeplane<< Starting ...'
println ""
println message_text
c.statusInfo = message_text

// #######################################################################################

// Start h2a-caller to process the highlighted text and get the annotations
try
{
	c.statusInfo = 'h2aFreeplane<< Starting h2a-caller ...' 

	// Find the first parent of the selected node "node" that contains a link to a PDF (searches three generations up)
	// @todo Of course the risks exists that we find the wrong node, if any other node contains a link to a PDF
	 node_with_pdf = H2A_utilityScripts.findFirstParentWithPdfLink( node )
	// If no node_with_pdf was found, print error message and end the script
	 if ( !node_with_pdf )
	 {
    	message_text = "h2aFreeplane<< Cannot find node/parent/grandparent/grand-grandparent of node ("+node.text+") that contains link to PDF."
		ui.errorMessage( message_text )
		c.statusInfo = message_text
		logger.severe(message_text, e) 
		return false
	 }
    // Extract command line options if present
     h2a_options = ""
     if ( node_with_pdf["update_procedure"] )
     {
        h2a_options = " '"+node_with_pdf["update_procedure"]+"'"
     }
     else
     {
        node_with_pdf.attributes.set("update_procedure","update_new")
     }

	// Get the path to the PDF
	 path_to_pdf = node_with_pdf.link.file.toString()
	
    // Adapt the path depending on the current PC
    // @todo This gives a nice standalone function to be used in different scripts (make standalone and callable)
     if ( operatingSystem=="Linux" )
     {
        // Check if the path contains the correct path to the Linux folder as the detected operating system is Linux
        if ( path_to_pdf.indexOf(path_to_lit_folder_Linux) == 0 )
        {
            // STATE: Operating system is Linux and path contains Linux folder
        }
        else if ( path_to_pdf.indexOf(path_to_lit_folder_Windows) == 0 )
        {
            // STATE: Operating system is Linux but path contains Windows folder
            // ACTION: Replace the Windows part of the path with the Linux part
             path_to_pdf = path_to_pdf.replace( path_to_lit_folder_Windows, path_to_lit_folder_Linux )
        }
        else
        {
		    // STATE: Cannot find any known path phrase
            // ACTION: Do nothing, show no error, do not log
        }
     }
     else if ( operatingSystem=="Windows" )
     {
        // Check if the path contains the correct path to the Windows folder as the detected operating system is Windows
        if ( path_to_pdf.indexOf(path_to_lit_folder_Windows) == 0 )
        {
            // STATE: Operating system is Windows and path contains Windows folder
        }
        else if ( path_to_pdf.indexOf(path_to_lit_folder_Linux) == 0 )
        {
            // STATE: Operating system is Windows but path contains Linux folder
            // ACTION: Replace the Linux part of the path with the Windows part
             path_to_pdf = path_to_pdf.replace( path_to_lit_folder_Linux, path_to_lit_folder_Windows )
        }
        else
        {
		    // STATE: Cannot find any known path phrase
            // ACTION: Do nothing, show no error, do not log
        }
     }

	// @todo We could/should check whether the pdf-file actually exists at this point, but this is automatically 
    //  checked by the Python script below. The Python script crashes if the pdf file does not exist, 
    //  which is catched below and "return false" is done

	path_file_output = path_to_h2a_tmp_directory + File.separator + filename_h2a_pdf_output
	
	if ( operatingSystem == "Linux" )
	{
        commandInit = ["bash","-c",'chmod +x "'+path_to_h2a_caller +'"']
	    def procInit = commandInit.execute()
        command = ["bash","-c",'"'+path_to_h2a_caller +'" "' + path_to_pdf + '" '+'"'+ path_file_output+'"' + h2a_options]
	}	
    else if ( operatingSystem ==  "Windows" )
	{
		command = "\""+path_to_h2a_caller.replace("\\","/")+"\" "+path_to_pdf.replace(" ","%20").replace("\\","/")+" "+path_file_output.replace(" ","%20").replace("\\","/") + h2a_options
	}

	if ( debugging >= 1 ) { println "h2aFreeplane<< h2a-caller command=" + command }
	
	// Execute the h2a-caller thereby waiting for its full completion
	def proc = command.execute()
	
	def b = new StringBuffer()
	proc.consumeProcessErrorStream(b)
	println proc.text
	println b.toString()

    // If b.toString() contains something, an error occured during the execution of the Python script. It 
    //  is important that we catch this here and "return false" or else the script might continue to run.
	if ( b.toString() )
	{
		println b.toString()
		message_text =  'h2aFreeplane<< Failed during execution of Python script.'
		c.statusInfo = message_text
		logger.severe(message_text + ' : ' +  b.toString()) 
		ui.errorMessage( message_text + ' : ' + b.toString() )
        return false
	}
}
catch (Exception e)
{ 
    println e
	message_text =  'h2aFreeplane<< Cannot start h2a-caller.' 
	c.statusInfo = message_text
	logger.severe(message_text, e) 
	ui.errorMessage( message_text + ' : ' + e )
	return false
}

// #######################################################################################

// Read the output of the h2a-caller (list of all annotations in the pdf) and apply it to the Freeplane nodes
try
{
	c.statusInfo = 'h2aFreeplane<< Reading and applying pdf-output ...' 
	
	// Extract the list of all previously imported annotations from note to detect manually deleted annotation nodes
	 // List to store all entries as list of strings, each string contains the annotation text and the annotation ID
      list_of_all_entries = []
     // List of all annotation IDs, which is an extract of list_of_all_entries
	  list_of_all_annotIDs = []
     // If the node_with_pdf contains a note, it also contains the list_of_all_entries
      if ( node_with_pdf.note )
      {
        // Extract the list from the note
         list_of_all_entries = node_with_pdf.note.split("\n")
        
        // Split each string into the annotation text and ID
         list_of_all_text_and_annotIDs = []
         list_of_all_entries.each
         {
            entry ->
            list_of_all_text_and_annotIDs.add(entry.split( ES ))
         }
        
        // Extract the second column as the list of all annotation IDs
         list_of_all_annotIDs = list_of_all_text_and_annotIDs.transpose()[1]
      }
	
	   // Collect children, grandchildren, and grand-grandchildren to work on "all" sub-nodes ("all"=here only three generations)
	    node_children123 = H2A_utilityScripts.findChildrenGenerations ( node_with_pdf, "annot_nodes_only" )
      
    list_of_annotIDs_in_pdf = []
	// Read the pdf-output and apply only the changes to the nodes
	new File( path_to_h2a_tmp_directory + File.separator + filename_h2a_pdf_output ).eachLine
	{
	   // Read each line, so each annotation
	    line ->
	   
       if ( debugging >= 4 ) { println "h2aFreeplane<< line="+line}
	   // Each line contains an annotation, the information is grouped into a single string (see "h2a_highlightedText_to_annotation.py")
	    line_split = line.split( ES )
	    annot_text = line_split[0].replace( line_break_replacer, '\n' )
	    annot_type = line_split[1]
	    annot_page = line_split[2]
	    annot_ID = line_split[3].toString()
	    annot_time = line_split[4].toString()
	    annot_colour = (line_split[5].toString())[1..-2].split(',').collect { it as Float }
	   
	   // Collect the annotations to later check if any was deleted in the PDF.
	   // @note We combine the annotation-ID with the annotation-page to create a unique string.
	   //       In case the annotation-ID was created by PyMuPDF the ID is only page-unique, 
	   //       so we need to append the page-number to make it PDF-unique.
	    list_of_annotIDs_in_pdf.add( annot_ID+" on page "+annot_page )
	   
	   if ( debugging >= 2 ) { println "h2aFreeplane<< annot_ID="+annot_ID+" ; annot_text="+annot_text +" ; annot_time="+annot_time}
	   
	   // Search for a child node that matches the current annotation ID "annot_ID"
	    annot_ID_Found = false

       if ( debugging >= 2 )
       {
           println "h2aFreeplane<< node_children123="
           println node_children123
       }
        
       // Loop over all sub-nodes of node_with_pdf to check "all" existing annotation nodes
       // @todo-optimize Use find/contains etc. functions and list_of_all_annotIDs to optimize the search
	    node_children123.any
	    {
	       child ->
	       	       
	       // Extract the annotation ID from the child's attribute
	        node_annot_ID = child["annot_ID"].toString()
	        node_annot_page = child["annot_page"]

           if ( color_newly_added_annotationNodes )
           {
               // Reset the font colour from green (marked new annotations) back to default (black), but only if error status is "ok" ("error" use text colour red)
               // @Note (@BUGfix) Changing the font color here is seen as modification of the node as if its content was changed. Therefore, a change in the pdf of this annotation will result in conflicting changes (change in pdf and in freeplane), even though the content of the annotation was not changed in freeplane. Therefore, the actual "lastModifiedAt" time is stored, then the text color is changed, and then the old/actual modification is written over the falsely changed lastModifiedAt time
                if ( child.style.getTextColor()==DARK_GREEN && child["annot_status"].contains("ok") )
                {
	                node_lastModified_tmp = child.lastModifiedAt
                    child.style.setTextColor()
                    child.lastModifiedAt = node_lastModified_tmp
                }
            }

	       if ( debugging >= 3 ) { println "h2aFreeplane<< child="+child+" ; annot_ID="+node_annot_ID+" ; on annot_page="+node_annot_page }

           // We search for the annotation by its ID and by the page. Usually the ID should be unique in the PDF,
           //  but if the ID was created by PyMuPDF then the ID is only page-unique, so unique on this page (which is useless),
           //  so we also need to check for the page of the annotation
           // @note For ".equals" to work here properly, we need to use ".toString()" on the strings to be compared [https://stackoverflow.com/questions/77081424/groovy-not-all-strings-that-are-equal-are-equal]
	        if ( annot_ID.equals( node_annot_ID ) && annot_page.equals( node_annot_page )  )
	        {
	           // State: Found the node that matches the annotation ID, so the corresponding node already exists
	           // The H2A-protocal is deleted in Freeplane, because it can be quite large, so it is not shown, but still exists in the PDF
	           // @todo Maybe put some of the information into attributes of node_with_pdf
	            if ( annot_text.contains('H2A-protocol:') )
	            {
	               child.delete()
	               annot_ID_Found = true
	               return true // break the node_children123.any, because we are done with this annot_ID
	            }
    		   // If the child.text contains the error phrase and the annot_status is error, the error is still unresolved
    		    else if ( child["annot_status"].contains("error") )
    		    {
        			if ( child.text.contains( error_phrase ) )
        			{
        				// @STATE: Found an annotation that was changed in the pdf and in Freeplane. The child.text still contains the error-phrase, so the error is still unresolved. The user needs to clean this thereby removing the catch phrase to again activate h2a on this annotation
        				annot_ID_Found = true
        				return true
        			}
    		    }
	           
	           // Extract the time when the annotation in this node was last modified in the PDF
	            node_annot_time = child["annot_modTime_PDF"].toString()
	           
	           if ( debugging >= 3 ) { println "h2aFreeplane<< time: pdf="+annot_time+" vs node="+node_annot_time }

	           // If the annot pdf time is unchanged, the pdf-output contains no new content -> skip
	           // @note Here it is still possible that the annotation was changed in Freeplane manually, so this check does not mean that the annotation is unchanged, but merely that no change results from the PDF
	           // @note For ".equals" to work here properly, we need to use ".toString()" on the strings to be compared
	            if ( annot_time.equals( node_annot_time ) )
	            {
    	           // State: The annotation already exists and it did not change in the PDF
	               println "h2aFreeplane<< Found annotation, but unchanged in pdf"
	               annot_ID_Found = true
	               return true // break the node_children123.any, because we are done with this annot_ID
	            }
	            else // annot_time can only be newer, so if it is not equal, it is newer
	            {
	               // State: The annotation node already exists and it changed in the pdf 
	                node_lastModified = child.lastModifiedAt.toString()
	                node_annot_time_Freeplane = child["annot_modTime_Freeplane"].toString()
	                
	               if ( debugging >= 3 ) { println "h2aFreeplane<< time: node last mod="+node_lastModified+" vs node_time_Freeplane="+node_annot_time_Freeplane }
	                
	               node_lastModified = child.lastModifiedAt
	               
	               // If the last modification of the node is equal to "node_annot_time_Freeplane", which is the last time h2a modified it, then h2a did the last modification, thus the user did not change the annotation node
	                if ( node_lastModified.toString().equals(node_annot_time_Freeplane) )
	                {
	                    // State: The annotation already exists, it changed in the pdf, but it did not change in Freeplane
	                    println "h2aFreeplane<< Found annotation, changed in pdf"
	                    // Action: Read the newer text from the pdf
	                    child.text = annot_text
	                    child["annot_modTime_PDF"] = annot_time

                        if ( colour_node_in_annotColour )
                        {
                            if (  ColorChecker.checkIfColorAccept(annot_colour,annotColour_to_be_ignored,annotColour_to_be_ignored_tolerance) )
                            {
                        		 // Assign RGB values from annot_colour to the background colour of the node
                                 child.style.backgroundColor = new Color(annot_colour[0],annot_colour[1],annot_colour[2],opacity_background_colour)
                            }
                            else
                            {
                        		 // If colour of pdf annotation shall be ignored, we set the background colour to "null".
                                 //  This will ensure that changing a background colour back to an ignored colour will also reset the node's colour
                                  child.style.backgroundColor = null
                            }
                        }

	                    // Update the time, because it changed by overwriting "child.text" above. It is important the the stored time "annot_modTime_Freeplane" is equal to the lastModifiedAt time for the above ".equals" to work
	                     child["annot_modTime_Freeplane"] = node_lastModified.toString()
	                }
	                else
	                {
	                    // State: The annotation already exists, it changed in the pdf and it changed in Freeplane
	                    println "h2aFreeplane<< Found annotation, changed in pdf and freeplane"
	                    // Action: Which one to take?
	                    // @todo Which one to take? But show ui.message with selection/merge

	                    if ( !child.text.contains( error_phrase ) && child["annot_status"] == "error" )
        			    {
            				// @STATE: We consider a node which was previously detected as error, due to changes in pdf and in freeplane.
            				//         We know this happened to this node, because of the annot_status. But the contradicting changes,
            				//         where resolved because even though the status is "error", the error-phrase in the child.text is missing.
            				//         Therefore, we can see this error as resolved and continue as usual.
        					child.attributes.set("annot_status","ok")
        					// @todo If the user manually changed the text colour, e.g. to blue, this will remove user colours
        					child.style.setTextColor()
        			    }
        			    else
        			    {
            				// We add the error-phrase to the text and set the annot_status to "error"
        	                child.text = error_phrase + "\n>PDF>\n" + annot_text+"\n>Freeplane>\n" + child.text
            				child.style.setTextColor(Color.red)
            				child.attributes.set("annot_status","error")
	                    }
	                }
	                // Update the time, because it changed by overwriting "child.text" above. It is important the the stored time "annot_modTime_Freeplane" is equal to the lastModifiedAt time for the above ".equals" to work
	                 child.lastModifiedAt = node_lastModified
	                annot_ID_Found = true
	                return true // break the node_children123.any, because we are done with this annot_ID
	           } // end Check of modTime-PDF
	        } // end Check of annot-ID
	   } // end node_children123.any
	   
	   // We looped over each node, but did not find a node with the annot_ID from the pdf.
	   //  And if we currently do not process the H2A-protocol, which should not be shown in Freeplane.
       //  Then we finally #CREATE the node.
	    if ( annot_ID_Found == false && !annot_text.contains('H2A-protocol:') )
	    {
    	  	if ( debugging >= 1 ) 
            {
                println "h2aFreeplane<< list="
                println list_of_all_annotIDs
            }
    	   // To make sure that this missing annot_ID was not deleted by the user previously, we check whether it has been previously imported
    	   // This ensures that annotation that were deleted by the user once, are not reimported. If you want to again import the annotation, open the note of the node_with_pdf (e.g. manually in Freeplane GUI) and delete the line containing the desired annotation (annot_text and annot_ID) from the note. This will cause the script to again import the annotation.
           // Check whether the list is empty or does not contain the currently processes annotation
            if ( !list_of_all_annotIDs || !list_of_all_annotIDs.contains( annot_ID ) )
    	    {
        	    // State: The list of all previously imported annotations is empty, or it does not contain the currently processed annotation
		    // @ACTION: Freshly create a node that relates to this annotation
    	        println "h2aFreeplane<< annot_ID not found"
    	        
    	        // Create a new child that is associated with the annotation
    	         child = node_with_pdf.createChild(annot_text)
    	       
    	        // Store the annotation related information as attributes to the node
        		 child.attributes.set("annot_page",annot_page)
        		 child.attributes.set("annot_type",annot_type)
        		 child.attributes.set("annot_ID",annot_ID)
        		 child.attributes.set("annot_modTime_PDF",annot_time)
        		 child.attributes.set("annot_status","ok")
        		 child.attributes.set("backup_path_to_pdf",path_to_pdf)

                 if ( colour_node_in_annotColour )
                 {
                    if (  ColorChecker.checkIfColorAccept(annot_colour,annotColour_to_be_ignored,annotColour_to_be_ignored_tolerance) )
                    {
                		 // Assign RGB values from annot_colour to the background colour of the node
                          child.style.backgroundColor = new Color(annot_colour[0],annot_colour[1],annot_colour[2],opacity_background_colour)
                    }
                 }

        		 child.link.text = 'menuitem:_H2aOpenPdfOnAnnotPage_on_single_node'

        		 lastModTime = child.lastModifiedAt.toString()
                 child.attributes.set("annot_modTime_Freeplane",lastModTime)

            	   if ( debugging >= 2 )
            	   {
                        println "h2aFreeplane<< after setting attributes: annot_time="+ annot_time
            	   }

                // Append the freshly added annotation to the list of imported annotation
    	         list_of_all_entries =  list_of_all_entries + [ annot_text.replace( '\n', line_break_replacer ) + ES + annot_ID ]
        	     
           	    if ( debugging >= 1 ) 
                {
            	    println "h2aFreeplane<< list_of_all_entries after="
                    println list_of_all_entries
                }

                if ( sort_newly_added_annotationNodes_by_page )
                {
        	        // Shift the node upwards until its annot_page fits to the annot_page of the node above
                    // @todo Put this into a function and make it callable depending on user-parameter (together with the above reset of dark_green
	                 // Collect node_children1 for sorting (repetitive code findChildrenGenerations)
                     // @note This must be done before every attempt to use "moveTo", because by "createChild" the list changes, therefore the indices change
	                  def node_children1 = []
	                  node_with_pdf.children.each
	                  {
	                    child1 ->
	                    if ( child1["annot_ID"] )
	                    {
		                    node_children1 = node_children1 + child1
	                    }
	                  }
                     // The counter starts at zero to be able to shift a node all the way to the top (index=0)
                      counter_i = 0
                      if ( color_newly_added_annotationNodes )
                      {
                        child.style.setTextColor(DARK_GREEN)
                      }
        	          node_children1.find
                      {
                        child1 ->
                        if ( child1["annot_page"] && (child1["annot_page"].toInteger() > annot_page.toInteger()) )
                        {
                            child.moveTo( node_with_pdf, counter_i )
                            return true
                        }
                        counter_i = counter_i + 1
                        return false
                      }
                } // end if (sort_newly_added_annotationNodes_by_page)
	        }
    	    else
    	    {
        	   // State: A node with the annotation ID does not exist currently, but it existed previously. Therefore, it must have been deleted by the user, thus we do not import it again -> do nothing
        	   //return true // end the function
    	    }
	   } // end if ( annot ID not found )
	} // end eachline

    // Store the updated list of imported annotations into the note of the note_with_pdf
     node_with_pdf.note = list_of_all_entries.join("\n")

    // Remove annotation nodes which where deleted in the PDF:
	 // We looped over each annotation in the pdf and tried to find matching nodes or create missing nodes for each annotation. 
	 // If there is an annotation node in the mindmap, which however does now not exist
	 // in the h2a_pdf-output.tmp, then this old node must have been deleted in the pdf in the meantime. Therefore, we also delete the associated node.
	 // Collect children, grandchildren, and grand-grandchildren that now after updating exist
	  node_children123_updated =  H2A_utilityScripts.findChildrenGenerations ( node_with_pdf, "annot_nodes_only" )
	  list_of_all_annotIDs_updated = []
	  node_children123_updated.each
	  {
          child ->
    	  list_of_all_annotIDs_updated = list_of_all_annotIDs_updated + [ child["annot_ID"]+" on page "+child["annot_page"] ]
	  }
      
	  list_of_annots_in_Freeplane_but_not_in_pdf = list_of_all_annotIDs_updated - list_of_annotIDs_in_pdf

      counter_failedToDelete = 0
      list_of_failed_annots = []
      // For some reason the .each will process a null element when the list is empty, therefore the two null checks were added
	  list_of_annots_in_Freeplane_but_not_in_pdf.each
	  {
          annot_ID_toBeDeleted ->
    	  ID_found = false
    	  // @todo-optimize A lot of potential for optimisation here
	 	   node_children123_updated.any
	       {
    	       child ->
    	       if ( child["annot_ID"] && child["annot_page"] && ( child["annot_ID"]+" on page "+child["annot_page"] ).equals(annot_ID_toBeDeleted) )
    	       {
        	       child.delete()
        	       //ui.informationMessage("deleted "+annot_ID_toBeDeleted+ " vs "+child["annot_ID"])
        	       ID_found = true
        	       return true
    	       }
	 	   }
          // Output a note that something went wrong when trying to delete the missing nodes. Limit the number 
          // of messages to three, so the user is not forced to click through e.g. 100 messages just to get back to the mindmap.
           if ( annot_ID_toBeDeleted && ID_found == false && counter_failedToDelete <= 3 )
           {
               list_of_failed_annots = list_of_failed_annots + [ annot_ID_toBeDeleted ]
               counter_failedToDelete = counter_failedToDelete + 1
           }
	  }
	  if ( counter_failedToDelete > 0 )
	  {
	      ui.informationMessage("h2aFreeplane<< Failed to remove annot_IDs, which were removed in the PDF.\nList:"+list_of_failed_annots.join("\n"))
	  }
    // ... finished removing annotations that where deleted in the pdf.
} 
catch (Exception e)
{ 
    println e
	message_text = 'h2aFreeplane<< Failed to read and apply pdf-output.' 
	c.statusInfo = message_text
	logger.severe(message_text, e) 
	ui.errorMessage( message_text + ' : ' + e )
	return false
}


// #######################################################################################

// Check for changes done manually to the nodes in Freeplane and output them to tmp to update the PDF
try
{
	c.statusInfo = 'h2aFreeplane<< Checking for manual changes in Freeplane ...' 
	
	// @todo Place the special time-format used in h2a into a function
	 Date date = new Date()
	 String datePart = date.format("yyyyMMdd")
	 String timePart = date.format("HHmmss")
	 String time_current_pdfFormat = "D:" + datePart + timePart + "+01'00"
	
	println "h2aFreeplane<< time_current_pdfFormat="+time_current_pdfFormat
	
	new File( path_to_h2a_tmp_directory + File.separator + filename_h2a_freeplane_changes ).withWriter("UTF-8")
	{
    	writer ->
    	
    	// Write the path to the PDF into the first line of the output-file. This is read by h2a_update_from_freeplane.py to load and modify the PDF.
	     writer.writeLine( path_to_pdf )

	    // Collect children, grandchildren, and grand-grandchildren
	    // @todo-optimize Should be similar to above, besides the freshly created nodes
	     node_children123 =  H2A_utilityScripts.findChildrenGenerations ( node_with_pdf, "annot_nodes_only" )

        // Loop over each sub-node to check whether it was changed manually in Freeplane and output only those that were changed
	     node_children123.each
	     {
    	    child ->
    	    
    	    // The trailing ".toString()" is important to get a proper string comparison [https://stackoverflow.com/questions/77081424/groovy-not-all-strings-that-are-equal-are-equal]
    	     def modTime_freeplane = child["annot_modTime_Freeplane"]
    	     def node_lastModified = child.lastModifiedAt.toString()
    	
    	    // If the lastModified time does not equal the modTime_freeplane done by this script,
    	    //  then the node text must have been modified manually, so output it to outputFileName
    	     if ( !node_lastModified.equals(modTime_freeplane) && child["annot_status"] != "error" )
    	     {
        	    if ( debugging >= 1 )
        	    {
        	        println "h2aFreeplane<< change of '"+child.text+"'"
        	        println "h2aFreeplane<< last mod="+node_lastModified+"; modTime_FP="+modTime_freeplane
    	        }
    	        
    	        // Apply a dummy modification to update lastModified
    	         child.text = child.text
    	        // Update the annotation time PDF. This is important to ensure that we do not again overwrite the annotation, when reading the pdf-output of this change
    	        child["annot_modTime_PDF"] = time_current_pdfFormat
    	        node_lastModified = child.lastModifiedAt
    	        child["annot_modTime_Freeplane"] = node_lastModified.toString()
    	        
    	        // Overwrite the actual modification time to unify the times
    	        // @todo Check why the lastModified times are so weired if done without overwriting
    	         child.lastModifiedAt = node_lastModified

                child_backgroundColour_string = ""
                // If the annotation nodes shall be coloured based on the colour of the pdf annotation,
                //  we also want to synch the colour of the annotation node back into the pdf.
                //  For this the background colour must be defined (child.style.backgroundColor==true) and not white (getRGB()!=-1)
                if ( colour_node_in_annotColour && child.style.backgroundColor && child.style.backgroundColor.getRGB()!=-1 )
                {
                    // Extract the rgb values of the node's background colour [code thanks to perplexity.ai]
                    child_backgroundColour_rgb = child.style.backgroundColor.getRGB()
                    child_backgroundColour_red =   ((child_backgroundColour_rgb >> 16) & 0xFF)/255
                    child_backgroundColour_green = ((child_backgroundColour_rgb >> 8) & 0xFF )/255
                    child_backgroundColour_blue =   (child_backgroundColour_rgb & 0xFF       )/255
                    child_backgroundColour_string = "[$child_backgroundColour_red,$child_backgroundColour_green,$child_backgroundColour_blue]"
                }
    	        
    	        // @todo Somehow centralise the order in which the data is stored (page, type, ID, ...)
    	         writer.writeLine (
    	                            child.text.replace('\n',line_break_replacer) + ES
    	                            + child["annot_page"] + ES
    	                            + child["annot_type"] + ES
    	                            + child["annot_ID"] + ES
    	                            + child["annot_modTime_PDF"] + ES
    	                            + child["annot_modTime_Freeplane"] + ES
    	                            + child_backgroundColour_string + ES
    	                          )
    	     } // end if ( node modified in Freeplane )
    	} // end for each sub-node
	} // end writing freeplane-changes
}
catch (Exception e)
{
    println e
	message_text = 'h2aFreeplane<< Failed to check for manual changes.' 
	c.statusInfo = message_text
	logger.severe(message_text, e) 
	ui.errorMessage( message_text + ' : ' + e )
	return false
}

// #######################################################################################

// Run h2a_update_from_Freeplane to apply the manual changes in Freeplane to the annotations in the pdf
try
{
	c.statusInfo = 'h2aFreeplane<< Applying manual changes from Freeplane to PDF ...' 
	
	path_file_changes = path_to_h2a_tmp_directory + File.separator +  filename_h2a_freeplane_changes
	println( path_file_changes )

	if ( operatingSystem == "Linux" )
	{
        command2Init = ["bash","-c",'chmod +x "'+path_to_h2a_update_from_Freeplane +'"']
	    def proc2Init = command2Init.execute()
		command2 = ["bash","-c",'"'+path_to_h2a_update_from_Freeplane +'" "'+ path_file_changes+'"']
	}	
	else if ( operatingSystem ==  "Windows" )
	{
		command2 = "\""+path_to_h2a_update_from_Freeplane.replace("\\","/")+"\" "+path_file_changes.replace(" ","%20").replace("\\","/")
	}

	if ( debugging >= 1 ) { println "h2aFreeplane<< h2a-update from Freeplane command=" + command2 }
	
	// Execute h2a_update_from_Freeplane thereby waiting for its full completion
	def proc2 = command2.execute()
	
	def b2 = new StringBuffer()
	proc2.consumeProcessErrorStream(b2)
	println proc2.text
    println b2.toString()

	if ( b2.toString() )
	{
		println b2.toString()
		message_text = 'h2aFreeplane<< Failed during execution of Python script h2a_update_from_Freeplane.'
		c.statusInfo = message_text
		logger.severe(message_text + ' : ' +  b2.toString()) 
		ui.errorMessage( message_text + ' : ' + b2.toString() )
        return false
	}

    // @todo Create or modify an extended user attribute for the pdf file to store e.g. that and when it was last processed by h2a

    // Change the pdf icon to mark processed pdfs
    //commandIcon = ["bash","-c",'gio set -t '+"'"+'string'+"'"+' "'+path_to_pdf +'" '+"'"+'metadata::custom-icon'+"'"+' "file://'+path_to_this_folder+'/docu/h2aFreeplane_icon.svg"']
	
    //ui.errorMessage("h2aFreeplane<< "+commandIcon.toString() )

    //def procIcon = commandIcon.execute()
	
	//def bIcon = new StringBuffer()
	//procIcon.consumeProcessErrorStream(bIcon)
	//println procIcon.text
    //println bIcon.toString()

	//if ( bIcon.toString() )
	//{
	//	ui.errorMessage("h2aFreeplane<< "+bIcon.toString() )
	//}


    
    // Get the current time to show it in the last statusInfo, so the user can see that the script started and finished and when this script finished last
     Date date_finished = new Date()
     String time_finished = date_finished.format("HH:mm:ss")
    
    message_text = 'h2aFreeplane<< Finished at ' + time_finished + '.' 
    println message_text
    print ""
    c.statusInfo = message_text
} 
catch (Exception e)
{
    println e
	message_text = 'h2aFreeplane<< Failed to apply Freeplane changes to PDF.' 
	c.statusInfo = message_text
	logger.severe(message_text, e) 
	ui.errorMessage( message_text + ' : ' + e )
	return false
}
