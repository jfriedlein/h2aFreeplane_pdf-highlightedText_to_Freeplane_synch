<map version="freeplane 1.12.1">
<!--To view this file, download free mind mapping software Freeplane from https://www.freeplane.org -->
<node TEXT="h2aFreeplane" FOLDED="false" ID="ID_696401721" CREATED="1610381621824" MODIFIED="1734939749063" LINK="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch" BACKGROUND_COLOR="#97c7dc" STYLE="oval">
<font SIZE="16" BOLD="true" ITALIC="true"/>
<hook NAME="MapStyle">
    <properties edgeColorConfiguration="#808080ff,#ff0000ff,#0000ffff,#00ff00ff,#ff00ffff,#00ffffff,#7c0000ff,#00007cff,#007c00ff,#7c007cff,#007c7cff,#7c7c00ff" show_icon_for_attributes="true" show_tags="UNDER_NODES" associatedTemplateLocation="template:/standard-1.6.mm" show_note_icons="true" fit_to_viewport="false"/>
    <tags category_separator="::"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node" STYLE="oval" UNIFORM_SHAPE="true" VGAP_QUANTITY="24 pt">
<font SIZE="24"/>
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="bottom_or_right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="default" ID="ID_271890427" ICON_SIZE="12 pt" COLOR="#000000" STYLE="fork">
<arrowlink SHAPE="CUBIC_CURVE" COLOR="#000000" WIDTH="2" TRANSPARENCY="200" DASH="" FONT_SIZE="9" FONT_FAMILY="SansSerif" DESTINATION="ID_271890427" STARTARROW="NONE" ENDARROW="DEFAULT"/>
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<richcontent TYPE="DETAILS" CONTENT-TYPE="plain/auto"/>
<richcontent TYPE="NOTE" CONTENT-TYPE="plain/auto"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.tags">
<font SIZE="10"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.attributes">
<font SIZE="9"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.note" COLOR="#000000" BACKGROUND_COLOR="#ffffff" TEXT_ALIGN="LEFT"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.selection" BACKGROUND_COLOR="#afd3f7" BORDER_COLOR_LIKE_EDGE="false" BORDER_COLOR="#afd3f7"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="bottom_or_right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important" ID="ID_67550811">
<icon BUILTIN="yes"/>
<arrowlink COLOR="#003399" TRANSPARENCY="255" DESTINATION="ID_67550811"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.flower" COLOR="#ffffff" BACKGROUND_COLOR="#255aba" STYLE="oval" TEXT_ALIGN="CENTER" BORDER_WIDTH_LIKE_EDGE="false" BORDER_WIDTH="22 pt" BORDER_COLOR_LIKE_EDGE="false" BORDER_COLOR="#f9d71c" BORDER_DASH_LIKE_EDGE="false" BORDER_DASH="CLOSE_DOTS" MAX_WIDTH="6 cm" MIN_WIDTH="3 cm"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="bottom_or_right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000" STYLE="oval" SHAPE_HORIZONTAL_MARGIN="10 pt" SHAPE_VERTICAL_MARGIN="10 pt">
<font SIZE="18"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="16"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="10"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,5"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,6"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,7"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,8"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,9"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,10"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,11"/>
</stylenode>
</stylenode>
</map_styles>
</hook>
<hook NAME="AutomaticEdgeColor" COUNTER="11" RULE="ON_BRANCH_CREATION"/>
<attribute NAME="name" VALUE="h2aFreeplane"/>
<attribute NAME="version" VALUE="v2.0" OBJECT="org.freeplane.features.format.FormattedObject|java.lang.String&amp;#x7c;v2.0|number:decimal:#0.####"/>
<attribute NAME="author" VALUE="jfriedlein"/>
<attribute NAME="freeplaneVersionFrom" VALUE="v1.6"/>
<attribute NAME="freeplaneVersionTo" VALUE=""/>
<attribute NAME="updateUrl" VALUE="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch"/>
<attribute NAME="downloadUrl" VALUE="https://github.com/jfriedlein/h2aFreeplane_pdf-highlightedText_to_Freeplane_synch"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      The homepage of this add-on should be set as the link of the root node.
    </p>
    <p>
      The basic properties of this add-on. They can be used in script names and other attributes, e.g. &quot;${name}.groovy&quot;.
    </p>
    <ul>
      <li>
        name: The name of the add-on, normally a technically one (no spaces, no special characters except _.-).
      </li>
      <li>
        author: Author's name(s) and (optionally) email adresses.
      </li>
      <li>
        version: Since it's difficult to protect numbers like 1.0 from Freeplane's number parser it's advised to prepend a 'v' to the number, e.g. 'v1.0'.
      </li>
      <li>
        freeplane-version-from: The oldest compatible Freeplane version. The add-on will not be installed if the Freeplane version is too old.
      </li>
      <li>
        freeplane-version-to: Normally empty: The newest compatible Freeplane version. The add-on will not be installed if the Freeplane version is too new.
      </li>
      <li>
        updateUrl: URL of the file containing information (version, download url) on the latest version of this add-on. By default: &quot;${homepage}/version.properties&quot;
      </li>
      <li>
        downloadUrl: add-on file download URL added to version.properties, by default ${homepage}/$name-$version.addon.mm&quot;
      </li>
    </ul>
  </body>
</html></richcontent>
<node TEXT="description" POSITION="top_or_left" ID="ID_785569324" CREATED="1734939579258" MODIFIED="1734939579268">
<edge COLOR="#ff0000"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      Description would be awkward to edit as an attribute.
    </p>
    <p>
      So you have to put the add-on description as a child of the <i>'description'</i>&#xa0;node.
    </p>
    <p>
      To translate the description you have to define a translation for the key 'addons.${name}.description'.
    </p>
  </body>
</html></richcontent>
<node TEXT="Freeplane script to organise highlighted text and notes from pdf files as Freeplane mindmap. This is very helpful to organise the information from many pdfs (usually spread and hidden in many annotations) as a mindmap, similar to Docear." ID="ID_899730347" CREATED="1734939767936" MODIFIED="1734939775284"/>
</node>
<node TEXT="changes" POSITION="top_or_left" ID="ID_1637068220" CREATED="1734939579270" MODIFIED="1734939579273">
<edge COLOR="#0000ff"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      Change log of this add-on: append one node for each noteworthy version and put the details for each version into a child node.
    </p>
  </body>
</html></richcontent>
</node>
<node TEXT="license" FOLDED="true" POSITION="top_or_left" ID="ID_748477451" CREATED="1734939579274" MODIFIED="1734939579279">
<edge COLOR="#00ff00"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      The add-ons's license that the user has to accept before she can install it.
    </p>
    <p>
      
    </p>
    <p>
      The License text has to be entered as a child of the <i>'license'</i>&#xa0;node, either as plain text or as HTML.
    </p>
  </body>
</html></richcontent>
<node TEXT="&#xa;This add-on is free software: you can redistribute it and/or modify&#xa;it under the terms of the GNU General Public License as published by&#xa;the Free Software Foundation, either version 2 of the License, or&#xa;(at your option) any later version.&#xa;&#xa;This program is distributed in the hope that it will be useful,&#xa;but WITHOUT ANY WARRANTY; without even the implied warranty of&#xa;MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the&#xa;GNU General Public License for more details.&#xa;" ID="ID_1415095454" CREATED="1734939579279" MODIFIED="1734939579279"/>
</node>
<node TEXT="preferences.xml" POSITION="top_or_left" ID="ID_567727912" CREATED="1734939579296" MODIFIED="1734939579300">
<edge COLOR="#ff00ff"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      <span style="color: #000000; font-family: SansSerif, sans-serif;">The child node contains the add-on configuration as an extension to mindmapmodemenu.xml (in Tools-&gt;Preferences-&gt;Add-ons). </span>
    </p>
    <p>
      <span style="color: #000000; font-family: SansSerif, sans-serif;">Every property in the configuration should receive a default value in <i>default.properties</i>&#xa0;node.</span>
    </p>
  </body>
</html></richcontent>
</node>
<node TEXT="default.properties" POSITION="top_or_left" ID="ID_1409497986" CREATED="1734939579301" MODIFIED="1734939579304">
<edge COLOR="#00ffff"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      These properties are used for:
    </p>
    <ul>
      <li>
        Each property defined in the preferences should have a default value in the attributes of this node.
      </li>
      <li>
        For each menu item with an icon add an attribute with the icon key (use developer tool menuItemInfo) as key and the icon path as value. Example: '${name}.icon': '/images/${name}-icon.png'
      </li>
    </ul>
  </body>
</html></richcontent>
</node>
<node TEXT="translations" POSITION="top_or_left" ID="ID_174096541" CREATED="1734939579305" MODIFIED="1734939579309">
<edge COLOR="#7c0000"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      The translation keys that this script uses. Define one child node per supported locale. The attributes contain the translations. Define at least
    </p>
    <ul>
      <li>
        'addons.${name}' for the add-on's name
      </li>
      <li>
        'addons.${name}.description' for the description, e.g. in the add-on overview dialog (not necessary for English)
      </li>
      <li>
        'addons.${name}.&lt;scriptname&gt;' for each script since it will be the menu title.
      </li>
    </ul>
  </body>
</html></richcontent>
<node TEXT="en" POSITION="top_or_left" ID="ID_1059463241" CREATED="1734939579309" MODIFIED="1734940261191">
<attribute NAME="addons.${name}" VALUE="h2aFreeplane"/>
<attribute NAME="addons.${name}.h2aFreeplane" VALUE="h2aFreeplane"/>
<attribute NAME="addons.${name}.UtilityScripts" VALUE="UtilityScripts"/>
<attribute NAME="addons.${name}.h2aOpenPdfOnAnnotPage" VALUE="h2aOpenPdfOnAnnotPage"/>
</node>
</node>
<node TEXT="deinstall" POSITION="top_or_left" ID="ID_935593604" CREATED="1734939579324" MODIFIED="1735029231016">
<edge COLOR="#00007c"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      List of files and/or directories to remove on uninstall
    </p>
  </body>
</html></richcontent>
<attribute_layout NAME_WIDTH="54 pt" VALUE_WIDTH="595.49998 pt"/>
<attribute NAME="delete" VALUE="${installationbase}/addons/${name}.script.xml"/>
<attribute NAME="delete" VALUE="${installationbase}/addons/${name}/*"/>
<attribute NAME="delete" VALUE="${installationbase}/lib/H2A_utilityScripts.groovy"/>
<attribute NAME="delete" VALUE="${installationbase}/scripts/h2aOpenPdfOnAnnotPage.groovy"/>
</node>
<node TEXT="scripts" POSITION="bottom_or_right" ID="ID_1936517094" CREATED="1734939579340" MODIFIED="1734939579348">
<edge COLOR="#007c00"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may contain multiple scripts. The node text defines the script name (e.g. insertInlineImage.groovy). The name must have a suffix of a supported script language like .groovy or .js and may only consist of letters and digits. The script properties have to be configured via attributes:
    </p>
    <p>
      
    </p>
    <p>
      * menuLocation: &lt;locationkey&gt;
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- Defines the menu location, defaults a sub menu 'main_menu_scripting/addons.${name}'.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;-&#xa0;Use developer tool menuItemInfo to inspect menu location keys.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * menuTitleKey: &lt;key&gt;
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- The menu item title will be looked up under the translation key &lt;key&gt; - don't forget to define its translation.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * executionMode: &lt;mode&gt;
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- The execution mode as described in the Freeplane wiki (http://freeplane.sourceforge.net/wiki/index.php/Scripting)
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- ON_SINGLE_NODE: Execute the script once. The <i>node</i>&#xa0;variable is set to the selected node.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- ON_SELECTED_NODE: Execute the script n times for n selected nodes, once for each node.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- ON_SELECTED_NODE_RECURSIVELY: Execute the script on every selected node and recursively on all of its children.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- In doubt use ON_SINGLE_NODE.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * keyboardShortcut: &lt;shortcut&gt;
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- Optional: keyboard combination / accelerator for this script, e.g. control alt I
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- Use lowercase letters for modifiers and uppercase for letters. Use no + signs.
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- The available key names are listed at http://download.oracle.com/javase/1.4.2/docs/api/java/awt/event/KeyEvent.html#VK_0
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;&#xa0;&#xa0;In the list only entries with a 'VK_' prefix count. Omit the prefix in the shortcut definition.
    </p>
    <p>
      
    </p>
    <p>
      * Permissions&#xa0;that the script(s) require, each either false or true:
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- execute_scripts_without_asking
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- execute_scripts_without_file_restriction: permission to read files
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- execute_scripts_without_write_restriction: permission to create/change/delete files
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- execute_scripts_without_exec_restriction: permission to execute other programs
    </p>
    <p>
      &#xa0;&#xa0;&#xa0;- execute_scripts_without_network_restriction: permission to access the network
    </p>
    <p>
      &#xa0;&#xa0;Notes:
    </p>
    <p>
      &#xa0;&#xa0;- The set of permissions is fixed.
    </p>
    <p>
      &#xa0;&#xa0;- Don't change the attribute names, don't omit one.
    </p>
    <p>
      &#xa0;&#xa0;- Set the values either to true or to false
    </p>
    <p>
      &#xa0;&#xa0;- In any case set execute_scripts_without_asking to true unless you want to annoy users.
    </p>
  </body>
</html></richcontent>
<node TEXT="h2aFreeplane.groovy" ID="ID_795269269" CREATED="1734939894247" MODIFIED="1734940031818">
<attribute NAME="menuTitleKey" VALUE="addons.${name}.h2aFreeplane"/>
<attribute NAME="menuLocation" VALUE="main_menu_scripting/addons.${name}"/>
<attribute NAME="executionMode" VALUE="on_single_node"/>
<attribute NAME="keyboardShortcut" VALUE=""/>
<attribute NAME="execute_scripts_without_asking" VALUE="true"/>
<attribute NAME="execute_scripts_without_file_restriction" VALUE="true"/>
<attribute NAME="execute_scripts_without_write_restriction" VALUE="true"/>
<attribute NAME="execute_scripts_without_exec_restriction" VALUE="true"/>
<attribute NAME="execute_scripts_without_network_restriction" VALUE="true"/>
</node>
</node>
<node TEXT="lib" POSITION="bottom_or_right" ID="ID_1463750689" CREATED="1734939579368" MODIFIED="1734939579372">
<edge COLOR="#7c007c"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may contain any number of nodes containing binary files (normally .jar files) to be added to the add-on's classpath.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The immediate child nodes contain the name of the file, e.g. 'mysql-connector-java-5.1.25.jar'). Put the file into a 'lib' subdirectory of the add-on base directory.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The child nodes of these nodes contain the actual files.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- Any lib file will be extracted in &lt;installationbase&gt;/&lt;addonname&gt;/lib.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The files will be processed in the sequence as seen in the map.
    </p>
  </body>
</html></richcontent>
</node>
<node TEXT="zips" POSITION="bottom_or_right" ID="ID_1519565703" CREATED="1734939579374" MODIFIED="1734939579377">
<edge COLOR="#007c7c"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may contain any number of nodes containing zip files.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The immediate child nodes contain a description of the zip. The devtools script releaseAddOn.groovy allows automatic zip creation if the name of this node matches a directory in the current directory.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The child nodes of these nodes contain the actual zip files.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- Any zip file will be extracted in the &lt;installationbase&gt;. Currently, &lt;installationbase&gt; is always Freeplane's &lt;userhome&gt;, e.g. ~/.freeplane/1.3.
    </p>
    <p>
      
    </p>
    <p>
      &#xa0;- The files will be processed in the sequence as seen in the map.
    </p>
  </body>
</html></richcontent>
<node TEXT="addons" ID="ID_36371542" CREATED="1734943597686" MODIFIED="1734943597686"/>
<node TEXT="lib" ID="ID_290260261" CREATED="1734944354557" MODIFIED="1734944354557"/>
<node TEXT="scripts" ID="ID_448294039" CREATED="1734948673462" MODIFIED="1734948673462"/>
</node>
<node TEXT="images" POSITION="bottom_or_right" ID="ID_1642138109" CREATED="1734939579378" MODIFIED="1734939579382">
<edge COLOR="#7c7c00"/>
<richcontent TYPE="NOTE">
<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may define any number of images as child nodes of the images node. The actual image data has to be placed as base64 encoded binary data into the text of a subnode.
    </p>
    <p>
      The images are saved to the <i>${installationbase}/resources/images</i>&#xa0;directory.
    </p>
    <p>
      
    </p>
    <p>
      The following images should be present:
    </p>
    <ul>
      <li>
        <i>${name}-icon.png</i>, like <i>oldicons-theme-icon.png</i>. This will be used in the app-on overview.
      </li>
      <li>
        <i>${name}-screenshot-1.png</i>, like <i>oldicons-theme-screenshot-1.png</i>. This will be used in the app-on details dialog. Further images can be included but they are not used yet.
      </li>
    </ul>
    <p>
      Images can be added automatically by releaseAddOn.groovy or must be uploaded into the map via the script <i>Tools-&gt;Scripts-&gt;Insert Binary</i>&#xa0;since they have to be (base64) encoded as simple strings.
    </p>
  </body>
</html></richcontent>
</node>
</node>
</map>
