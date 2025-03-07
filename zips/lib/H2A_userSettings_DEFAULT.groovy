// IMPORTANT:
//  If you want to change the user settings, rename this file to "H2A_userSettings.groovy" (so remove the trailing "_DEFAULT") and rename the class "H2A_userSettings_DEFAULT" to "H2A_userSettings".
//  This will activate the user settings below and will ensure that the file is not overwritten when updating Freeplane.

// Changes to this file, require a restart of Freeplane, because Freeplane only reads this file on start-up

// Import colours for printing text node.text red when an error occurs
import java.awt.Color

class H2A_userSettings_DEFAULT
{
    // Parameters for h2aFreeplane.groovy
    final static boolean sort_newly_added_annotationNodes_by_page = true
    final static Color color_of_added_annotationNodes = new Color(0,102,0) // DARK_GREEN
    final static boolean colour_node_in_annotColour = true
    final static float opacity_background_colour = 0.3
    final static annotColour_to_be_ignored = [1,1,0] // yellow
    final static float annotColour_to_be_ignored_tolerance = 0.25
    final static boolean add_backup_filepath_attribute = true

    // Parameters for h2aOpenPdfOnAnnotPage.groovy
    final static String pdf_viewer_Linux = "default"
    final static String pdf_viewer_Windows = "default"

    // Parameters for both
    final static String path_to_lit_folder_Linux = ""
    final static String path_to_lit_folder_Windows = ""
}
