Android-simple-XML-Parser
=========================

Simple class for pulling out ids of views from an Android XML layout file

Instructions
------------

The class only contains a single constructor and a single method.  The method takes a parameter of type `Context`.  Presumably the class will be initialized from within an Android Activity, in which case `this` is the suggested value for the parameter (if the code is in a `Fragment` then the `getActivity` method should be used).

The method, `getElementIds()`, takes two parameters: the resource ID of the layout file to be parsed, and a `String` which is the name of the View type requested.  A number of View type names are defined as class constants.  The method returns an `int` Array object which contains the id's of all the Views of the requested type in the file, in order of appearance in the XML file.  Dynamically added Views will not be scanned, because the parser reads the original layout file.  On the other hand, if you're adding Views dynamically, you ought to have no trouble getting their id's.  If the View's 'id' attribute is not explicitly defined in the layout file, even though the operating system necessarily assigns it an id, this method can not access it, and returns 0 instead for that particular View.

Note
----

The core of the `getELementIds` method depends on the assumption that the attribute index of 'id' is always 0. So far I've had no trouble with this, but it may not work under all circumstances.  I wouldn't have to depend on such an assumption, but it seems there's a bug in the implementation of the Android method `getIdResourceAttribute` (from class XMLBlock).  A bug report has been filed.

Sample Code
-----------

This is code applicable to a simple verification activity, where the user has to check a number of boxes in order to confirm they're applicable to move on to the next stage (Activity).  This could be the code for the button's onClick method.  Instead of accessing the checkboxes' resource Ids one at a time, which is both inefficient and hard to maintain, if, say, a change to the number of checkboxes is made once in a while, the XMLLayoutParser class allows us to grab all the Ids automatically.

    public void goToNextActivity(View v){
    		XMLLayoutParser parser = new XMLLayoutParser(this);
    		int[] checkboxes=null;
    		try {
    			checkboxes = parser.getElementIds(R.layout.activity_confirmation, XMLLayoutParser.CHECKBOX);
    		} catch (XmlPullParserException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		if(checkboxes != null){
        		boolean allChecked = true;
        		for(int id:checkboxes){
        			CheckBox cbox = (CheckBox)findViewById(id);
        			if(!cbox.isChecked())
        				allChecked = false;
        		}
        		if(allChecked){
        			Intent intent = new Intent(this,TourMainMenuActivity.class);
        			startActivity(intent);
        		}else{
        			TextView message = (TextView)findViewById(R.id.message_unprepared);
        			message.setVisibility(View.VISIBLE);
        		}
    		}
    	}
