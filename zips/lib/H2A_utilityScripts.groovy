// Utility scripts based on ["https://docs.freeplane.org/scripting/Your_own_utility_script_library.html"]
// @IMPORTANT In Freeplane under Tools->Preferences->Plugins->"Script classpath" set the path to the folder containing this script

def static get_operatingSystem()
{
    // Determine the operating system to choose the Windows or Linux built of the Python-executables
    // [https://stackoverflow.com/questions/4689240/detecting-the-platform-window-or-linux-by-groovy-grails]
    // @todo For some reason "System.properties['os.name'].toLowerCase()" cannot be assigned to a variable like "operatingSystem_tmp = System.properties['os.name'].toLowerCase()"
    if ( System.properties['os.name'].toLowerCase().contains('windows') )
    {
        return "Windows";
    }
    else if ( System.properties['os.name'].toLowerCase().contains('linux') )
    {
        return "Linux";
    }
    else
    {
        ui.errorMessage("get_operatingSystem<< Cannot determine operating system="+System.properties['os.name']+" as 'Windows' or 'Linux'. Currently only Windows and Linux are supported.")
    }
}


// @todo try using find(*) or findAll(*) from [https://docs.freeplane.org/api/org/freeplane/api/ControllerRO.html#findAll()]
def static findChildrenGenerations ( node_with_pdf, node_type )
{
    // @todo-optimize Easily optimisable, e.g. by calling the function again on the sub-nodes etc.
	def node_children1 = []
	def node_children2 = []
	def node_children3 = []


    if ( node_type.equals("annot_nodes_only") )
    {
	   // Collect children, grandchildren, and grand-grandchildren to work on "all" sub-nodes
       // @note It is possible that a child is not an annotation-node, but that a child of this non-annotation-node is still an annotation-node
		// Collect all children of this node
		node_with_pdf.children.each
		{
		   child1 ->
		   if ( child1["annot_ID"] )
		   {
    		   node_children1 = node_children1 + child1
		   }
		}

		// Collect all grandchildren (children2)
        def node_children2_all = []
		node_with_pdf.children.each
		{
		   node2 ->
		   node2.children.each
		   {
    		   child2 ->
               node_children2_all = node_children2_all + child2
		   	   if ( child2["annot_ID"] )
    		   {
        		   node_children2 = node_children2 + child2
    		   }
		   }
		}

		// Collect all grand-grandchildren (children3)
		node_children2_all.each
		{
		   node3 ->
		   node3.children.each
		   {
    		   child3 ->
		   	   if ( child3["annot_ID"] )
    		   {
        		   node_children3 = node_children3 + child3
    		   }
		   }
		}

		return (node_children1 + node_children2 + node_children3)
	}
	else if ( node_type.equals("all") )
	{
	   // Collect children, grandchildren, and grand-grandchildren to work on "all" sub-nodes
		// Collect all children of this node
		node_children1 = node_with_pdf.children
		
		// Collect all grandchildren (children2)
		node_children1.each {
		   child2 ->
		   node_children2 = node_children2 + child2.children
		}
		
		// Collect all grand-grandchildren (children3)
		node_children2.each {
		   child3 ->
		    node_children3 = node_children3 + child3.children
		}

		return (node_children1 + node_children2 + node_children3)
    }
    else
    {
     	ui.errorMessage("findChildrenGenerations<< Called with unknown node_type="+node_type+".")
    }
}

def static findFirstParentWithPdfLink( node_selected )
{
	   // Find the first parent generation that contains a link to a PDF
	   if ( node_selected.link.text && node_selected.link.text.contains(".pdf") )
	   {
		return node_selected
	   }
	   else if ( node_selected.parent && node_selected.parent.link.text && node_selected.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent
	   }
	   else if ( node_selected.parent.parent && node_selected.parent.parent.link.text && node_selected.parent.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent.parent
	   }
	   else if ( node_selected.parent.parent.parent && node_selected.parent.parent.parent.link.text && node_selected.parent.parent.parent.link.text.contains(".pdf") )
	   {
		return node_selected.parent.parent.parent
	   }
	   else
	   {
		//ui.errorMessage("h2aFreeplane<< Cannot find node/parent/grandparent/grand-grandparent of node ("+node_selected.text+") that contains link to PDF")
		return null
   	   }
}

