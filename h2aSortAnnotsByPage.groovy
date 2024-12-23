// E. g. if you have already ran h2a on a pdf and have annotation nodes in Freeplane, now you add another highlight e.g. on the first page, but due to the way h2a currently works, new annotations are just added to the parent node such that they are placed at the bottom ot the childs, but you want the annotation from the first page to be on top to give the list some structure, running this script will sort all annotations by the annot_page (page number in pdf where annotation is located), such that annotation on the first page will be shifted to the top, however note that this changes all childs, so will change maybe purposeful reordering of annotations
// @todo Ideal would be a script which just operates on the newly inserted annotation and shifts it upwards below the last annotation with the same annot_page
// [https://docs.freeplane.org/scripting/Scripts_collection.html#sort-child-nodes-alphabetically-by-length-or-by-other-properties]
def sorted = new ArrayList(node.children).sort{ it['annot_page'].num0 }
sorted.eachWithIndex { it, i ->
    it.moveTo(node, i)
}
// @ExecutionModes({ON_SELECTED_NODE, ON_SELECTED_NODE_RECURSIVELY})
