package tabularprint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Apostolos Demertzis
 * @version 1.0
 */
public class TabularPrint {

    public static final int HORIZONTAL_RIGHT = 1;
    public static final int HORIZONTAL_LEFT = 2;
    public static final int HORIZONTAL_CENTER = 3;
    
    public static final int VERTICAL_TOP = 4;
    public static final int VERTICAL_BOTTOM = 5;
    public static final int VERTICAL_MIDDLE = 6;
    
    private String horizontal = "\u2500";        // ─
    private String cross = "\u253C";             // ┼
    private String horizontalStart = "\u251C";   // ├
    private String horizontalEnd = "\u2524";     // ┤
    private String horizontalUp = "\u2534";      // ┴
    private String horizontalDown = "\u252C";    // ┬

    private String vertical = "\u2502";          // │
    
    private String upLeftCorner = "\u250C";      // ┌
    private String downLeftCorner = "\u2514";    // └
    private String upRightCorner = "\u2510";     // ┐
    private String downRightCorner = "\u2518";   // ┘
    
    private String paddingChar = " ";
    
    // data columns and column separators
    private final ArrayList<Column> allColumns = new ArrayList<>();
    
    // data columns only, excluding separators
    private final ArrayList<Column> dataColumns = new ArrayList<>();
    
    // the final text to be printed
    private final StringBuilder textBuilder = new StringBuilder();
    
    // the table title. null means there is no title
    private String title = null;
    
    // auxiliar boolean to control the printing of headers and title
    private boolean isFirstTime = true;
    
    private boolean showBorder = false;       // show or not the table title
    private boolean showHeaders = false;      // show or not the headers
    private boolean showRowSeparators = false;// show or not a line separator between rows
    private boolean showTitleSeparator = true;
    private boolean showHeadersSeparator = true;

    /* *********************************************************************
                              a d d C o l u m n ()
       ********************************************************************* */
    /**
     * Adds a new column to this {@code TabularPrint}.
     * We have to define all parameters of the newly created column.
     * 
     * @param width the new column width
     * @param header the new column header. Pass an empty string for no header
     * @param horizAlign the horizontal alignment of every cell in this column. 
     * Possible values are one of the following static constants {@code HORIZONTAL_RIGHT}, 
     * {@code HORIZONTAL_LEFT}, {@code HORIZONTAL_CENTER}
     * @param vertAlign the vertical alignment of every cell in this column. 
     * Possible values are one of the following static constants {@code VERTICAL_TOP}, 
     * {@code VERTICAL_BOTTOM},  {@code VERTICAL_MIDDLE}
     * 
     * @return the newly created column for chain invocation
     */
    public Column addColumn(int width, String header, int horizAlign, int vertAlign){
        Column c = new Column();
        c.width(width);
        c.header(header);
        c.horAlign = horizAlign;
        c.verAlign = vertAlign;
        c.isSeparator = false;
        dataColumns.add(c);
        allColumns.add(c);
        return c;
    }
    
    
    /**
     * Works just like {@link addColumn(int, String, int, int)} except for the {@code vertcAlign} 
     * which is set as {@code VERTICAL_TOP}. 
     *
     * @see TabularPrint#addColumn(int, String, int, int)
     * 
     * @param width the new column width
     * @param header the new column header. Pass an empty string for no header.
     * @param horizAlign the horizontal alignment of every cell in this column. 
     * Possible values are one of the following static constants {@code HORIZONTAL_RIGHT},
     * {@code HORIZONTAL_LEFT}, {@code HORIZONTAL_CENTER}
     * 
     * @return the newly created column for chain invocation
     */
    public Column addColumn(int width, String header, int horizAlign){
        return addColumn(width, header, horizAlign, VERTICAL_TOP);
    }
    
    
    /**
     * Works just like {@link addColumn(int, String, int, int)} except for the {@code horizAlign} 
     * which is set as {@code HORIZONTAL_LEFT} and {@code vertAlign} which is set as 
     * {@code VERTICAL_TOP}. 
     *
     * @see TabularPrint#addColumn(int, String, int, int)
     * 
     * @param width the new column width
     * @param header the new column header. Pass an empty string for no header.
     * 
     * @return the newly created column for chain invocation
     */
    public Column addColumn(int width, String header){
        return addColumn(width, header, HORIZONTAL_LEFT, VERTICAL_TOP);
    }
    
    
    /**
     * Works just like {@link addColumn(int, String, int, int)} except for the {@code header}, 
     * which is set as an empty {@code String}, {@code horizAlign} which is set as 
     * {@code HORIZONTAL_LEFT} and {@code vertcAlign} which is set as {@code VERTICAL_TOP}. 
     *
     * @see TabularPrint#addColumn(int, String, int, int)
     * 
     * @param width the new column width
     * 
     * @return the newly created column for chain invocation
     */
    public Column addColumn(int width){
        return addColumn(width, "", HORIZONTAL_LEFT, VERTICAL_TOP);
    }
    
    
    /**
     * Works just like {@link addColumn(int, String, int, int)} with all parameters set as follows:
     * {@code width} is set to 12, {@code header} is set as an empty {@code String} 
     * {@code horizAlign} which is set as {@code HORIZONTAL_LEFT} and {@code vertcAlign} 
     * which is set as {@code VERTICAL_TOP}. 
     *
     * @see TabularPrint#addColumn(int, String, int, int)
     * 
     * @return the newly created column for chain invocation
     */
    public Column addColumn(){
        return addColumn(12, "", HORIZONTAL_LEFT, VERTICAL_TOP);
    }
    
    
    /* *********************************************************************
                          C O L U M N   S E P A R A T O R
       ********************************************************************* */
    /**
     * Adds a new column as a column separator. The column separator is added at the end 
     * of the list of the already defined columns.
     * 
     * @param separatorChar the {@code String} that separates the two adjacent columns
     */
    public void addColumnSeparator(String separatorChar){
        Column c = new Column();
        c.width(separatorChar.length());
        c.header(separatorChar);
        c.horAlign = HORIZONTAL_CENTER;
        c.isSeparator = true;
        allColumns.add(c);
    }
    
    
    /**
     * Works just like {@link  addColumnSeparator(String)} with the {@code String} parameter 
     * set as \u2502.
     * 
     * @see TabularPrint#addColumnSeparator(String)
     */
    public void addColumnSeparator(){
        TabularPrint.this.addColumnSeparator(vertical);
    }
    
    
    /* *********************************************************************
                 T I T L E   and   S H O W   D E C O R A T I O N
       ********************************************************************* */
    /**
     * Sets the title for this {@code TabularPrint}.
     * Title is to be printed above the header. 
     * 
     * @param title the title to be set
     */
    public void setTitle(String title){ this.title = title; }
    
    
    /**
     * Turns headers ON or OFF according to the given boolean parameter.
     * 
     * @param b {@code true} if we want to print the headers of this {@code TabularPrint}.
     * Otherwise {@code false}.
     */
    public void showHeaders(boolean b){ showHeaders = b; }
    
    
    /**
     * Shows a border around the {@code TabularPrint}, if the given boolean is set to {@code true}.
     * 
     * @param b {@code true} if we want to print a border around this {@code TabularPrint}.
     */
    public void showBorder(boolean b){ showBorder = b; }

    
    /**
     * Turns the printing of row separator ON or OFF, according to the given boolean parameter.
     * Row separator is a horizontal line between adjacent rows.
     * 
     * @param b {@code true} if we want to print a border around this {@code TabularPrint}.
     */
    public void showRowSeparators(boolean b){ showRowSeparators = b; }
    
    
    /**
     * Shows a horizontal line below the title.
     * 
     * @param b {@code true} if we want to print a horizontal line below the title.
     */
    public void showTitleSeparator(boolean b){ showTitleSeparator = b; }
    
    
    /**
     * Shows a horizontal line below the headers.
     * 
     * @param b {@code true} if we want to print a horizontal line below the headers.
     */
    public void showHeadersSeparator(boolean b){ showHeadersSeparator = b; }
    
    
    /* *********************************************************************
                      P R I N T I N G   M E T H O D S
       ********************************************************************* */
    /**
     * Prints the given values as consecutive cells in a single row.
     * 
     * 
     * @param cells the values to be printed
     */
    public void printRow(Object... cells){
        // 1. convert cells to array
        // -------------------------
        ArrayList<Object> arraylist = new ArrayList<>();
        for (Object obj : cells){
            if (obj == null){
                arraylist.add("");
            }
            else if (obj instanceof Object[]){
                // cast to Object[] and add all to arraylist
                arraylist.addAll(Arrays.asList((Object[]) obj));
            }
            // if a cell is array then iterate over the array's elements
            else if (obj.getClass().isArray()){
                int arrlength = Array.getLength(obj);
                for(int i = 0; i < arrlength; i++)
                   arraylist.add(Array.get(obj, i));
            }
            else arraylist.add(obj);
        }
        
        Object[] array = arraylist.toArray();
        
        // 2. check for the number of elements
        // --------------------------------
        // provided data is less than expected
        // we should pad the missing data with spaces
        if (array.length < dataColumns.size()){
            Object[] padCells = new Object[dataColumns.size()];
            System.arraycopy(array, 0, padCells, 0, array.length);
            for (int i = array.length; i < dataColumns.size(); i++)
                padCells[i] = " ".repeat(dataColumns.get(i).width);
            array = padCells;
        }
        // provided data is more than expected
        // we should ignore the excess data
        if (array.length > dataColumns.size()){
            Object[] newCells = new Object[dataColumns.size()];
            System.arraycopy(array, 0, newCells, 0, dataColumns.size());
            array = newCells;
        }
        
        
        // 3. print headers & title
        // ------------------------
        // for the very first time print the headers and the title
        if (isFirstTime){
            isFirstTime = false;
            if (showBorder) addBorderSides();
            printHeadersAndTitle();
        }
        
        // 4. convert the Object array to a String array
        // ---------------------------------------------
        String[] forPrint = new String[dataColumns.size()];
        for (int i = 0; i < array.length; i++){
            forPrint[i] = objectToString(array[i], dataColumns.get(i));
        }
        
        // 5. emulate printing in order to count the number of lines needed for each cell
        // ------------------------------------------------------------------------------
        int[] numOfLines = new int[array.length];// these are the number of lines for each cell
        // initialize number of lines to 1
        for (int j = 0; j < numOfLines.length; j++)
            numOfLines[j] = 1;
        
        // make a copy of the elements to print,
        // to be used by the printing emulation
        String[] forPrint2 = new String[forPrint.length];
        System.arraycopy(forPrint, 0, forPrint2, 0, forPrint.length);
        
        // do the printing emulation
        while (!isEmpty(forPrint2)){
            for (int i = 0; i < forPrint2.length; i++){
                forPrint2[i] = printCell(dataColumns.get(i), forPrint2[i], false);
                if (!forPrint2[i].isEmpty())
                    numOfLines[i]++;
            }
        } // at this point numOfLines[] contains the number of lines for each cell
        
        // 6. pad the rows according to vertical aligment
        // -------------------------------------------
        // find the maximum number of lines among all cells
        int maxLines = 0;
        for (int lines: numOfLines){
            if (lines > maxLines)
                maxLines = lines;
        }
        
        // add the proper number of spaces before the data to print
        for (int i = 0; i < forPrint.length; i++){
            switch (dataColumns.get(i).verAlign) {
                case VERTICAL_TOP -> {
                    // do nothing
                }
                case VERTICAL_BOTTOM -> {
                    int num = maxLines - numOfLines[i];
                    forPrint[i] = " ".repeat(num*dataColumns.get(i).width).concat(forPrint[i]);
                }
                case VERTICAL_MIDDLE -> {
                    int num = (maxLines - numOfLines[i]) / 2;
                    forPrint[i] = " ".repeat(num*dataColumns.get(i).width).concat(forPrint[i]);
                }
                default -> {
                    // do nothing, that is the same as VERTICAL_TOP
                }
            }
        }
        
        // 7. do the actual printing
        // ----------------------
        while (!isEmpty(forPrint)){
            int colIndex = 0;
            for (Column col : allColumns) {
                if (col.isSeparator)
                    printCell(col, col.header, true);
                else{
                    forPrint[colIndex] = printCell(col, forPrint[colIndex], true);
                    colIndex++;
                }
            }
            textBuilder.append("\n");
        }
        if (showRowSeparators) printRowSeparator();
    }
    
    
    /**
     * Prints the given values as consecutive cells in a single row.
     * The same as {@link printRow(Object[])} except for the type parameter. This method accepts
     * a list of printing elements, instead of an array.
     * 
     * @param list the list of values to be printed
     */
    public void printRow(List<Object> list){
        Object[] array = new Object[list.size()];
        array = list.toArray(array);
        TabularPrint.this.printRow(array);
    }
    
    
    /**
     * Prints an 'empty' row, that is a row with no data.
     * The specified {@code String} is the repeated character which fills the 'empty' line.
     * Use the method {@link printBlankRow()} for a trully empty line, that is the
     * repeated {@code String} will be the space character.
     * 
     * @param padd the repeated String that fills the 'empty' row
     */
    public void printBlankRow(String padd){
        String[] blanks = new String[dataColumns.size()];
        int i = 0;
        for (Column col : dataColumns){
            blanks[i++] = padd.repeat(col.width);
        }
        printRow((Object[]) blanks);
    }
    
    
    /**
     * Works like {@link printBlankRow(String)} but
     * the repeated String is the space character.
     * The result is a trully empty line.
     */
    public void printBlankRow(){
        printBlankRow(" ");
    }
    
    
    /* *********************************************************************
                             G E T   M E T H O D
       ********************************************************************* */
    /**
     * Returns this {@code TabularPrint} object as a {@code String}.
     * We can call this method as many times as we want. It does not
     * affect the current object state. For example, we can still print
     * new rows after the invocation of this method.
     * However, we cannot change the general parameters of the
     * {@code TabularPrint} after the first invocation of this method.
     * For example, we cannot alter the {@code showHeaders(boolean)}
     * behavior after calling the {@code getString()} method.
     * 
     * @return 
     */
    public String getString(){
        if (showRowSeparators){// in this case, we have to remove the last row separator
            // count the row width
            int rowWidth = 0;
            for (Column col : allColumns)
                rowWidth += col.width;
            textBuilder.delete(textBuilder.length() - rowWidth - 1, textBuilder.length());
        }
        
        // draw the do
        if (showBorder){ // regardless of showHeaders
            for (int i = 0; i < allColumns.size(); i++){
                Column col = allColumns.get(i);

                if (col.isSeparator && col.header.equals(vertical) && i == 0)
                    textBuilder.append(downLeftCorner);
                else if (col.isSeparator && col.header.equals(vertical) &&
                                                                    i == allColumns.size() - 1)
                    textBuilder.append(downRightCorner);
                else if (col.isSeparator && col.header.equals(vertical))
                    textBuilder.append(horizontalUp);
                else
                    textBuilder.append(horizontal.repeat(col.width));
            }
            textBuilder.append("\n");
        }
            
        return textBuilder.toString();
    }
    
    
    /* *********************************************************************
        C H A N G E   B O R D E R   &   P A D D I N G   C H A R A C T E R S
       ********************************************************************* */
    /**
     * Sets the character to be used for padding.
     * {@code TabularPrint} uses this character every time it needs to leave an empty space.
     * The default character for padding is, of course, the space character, but this can
     * be changed by calling this method.
     * 
     * @param s the character to be used in place of the space character 
     */
    public void setPaddingChar(String s){ paddingChar = s; }
    
    
    /**
     * Sets the character to be used for the inner point of a horizontal line.
     * The default value is the dash character '─'.
     * 
     * @param horizontal the character for printing the inner point of a horizontal line.
     */
    public void setHorizontalChar(char horizontal) {
        this.horizontal = Character.toString(horizontal);
    }

    
    /**
     * Sets the character to be used for the inner intersection point
     * between a horizontal and a vertical line.
     * The default value is the cross character '┼'
     * 
     * @param cross the character for printing intersection point 
     * between horizontal and vertical line
     */
    public void setCrossChar(char cross) {
        this.cross = Character.toString(cross);
    }

    
    /**
     * Sets the character to be used for the start point of a horizontal line.
     * The default value is '├'
     * 
     * @param horizontalStart the character for printing 
     * the start point of a horizontal line
     */
    public void setHorizontalStartChar(char horizontalStart) {
        this.horizontalStart = Character.toString(horizontalStart);
    }

    
    /**
     * Sets the character to be used for the end point of a horizontal line.
     * The default value is '┤'
     * 
     * @param horizontalEnd the character for printing the end point of a horizontal line
     */
    public void setHorizontalEndChar(char horizontalEnd) {
        this.horizontalEnd = Character.toString(horizontalEnd);
    }

    
    /**
     * Sets the character to be used for the bottom intersection point 
     * between a horizontal and a vertical line.
     * The default value is '┴'
     * 
     * @param horizontalUp the character for printing the bottom intersection point 
     * between a horizontal and a vertical line.
     */
    public void setHorizontalUpChar(char horizontalUp) {
        this.horizontalUp = Character.toString(horizontalUp);
    }

    
    /**
     * Sets the character to be used for the top intersection point 
     * between a horizontal and a vertical line.
     * The default value is '┬'
     * 
     * @param horizontalDown the character for printing the top intersection point 
     * between a horizontal and a vertical line.
     */
    public void setHorizontalDownChar(char horizontalDown) {
        this.horizontalDown = Character.toString(horizontalDown);
    }

    
    /**
     * Sets the character to be used for the inner point of a vertical line.
     * The default value is the pipe character '│'.
     * 
     * @param vertical the character for printing the inner point of a vertical line.
     */
    public void setVerticalChar(char vertical) {
        this.vertical = Character.toString(vertical);
    }

    
    /**
     * Sets the character to be used for the top left corner.
     * The default value is '┌'
     * 
     * @param leftCornerUp the character for printing the top left corner.
     */
    public void setLeftCornerTopChar(char leftCornerUp) {
        this.upLeftCorner = Character.toString(leftCornerUp);
    }

    
    /**
     * Sets the character to be used for the bottom left corner.
     * The default value is '└'
     * 
     * @param leftCornerDown the character for printing the bottom left corner.
     */
    public void setLeftCornerBottomChar(char leftCornerDown) {
        this.downLeftCorner = Character.toString(leftCornerDown);
    }

    
    /**
     * Sets the character to be used for the top right corner.
     * The default value is '┐'
     * 
     * @param rightCornerUp the character for printing the top right corner.
     */
    public void setRightCornerTopChar(char rightCornerUp) {
        this.upRightCorner = Character.toString(rightCornerUp);
    }

    
    /**
     * Sets the character to be used for the bottom right corner.
     * The default value is '┘'
     * 
     * @param rightCornerDown the character for printing the bottom right corner.
     */
    public void setRightCornerBottomChar(char rightCornerDown) {
        this.downRightCorner = Character.toString(rightCornerDown);
    }
    
    
    /* *********************************************
         P R I V A T E   P R I N T   M E T H O D S
       ********************************************* */
    private void printTitle(){
        // find the row width
        int rowWidth = 0;
        for (Column col : allColumns)
            rowWidth += col.width;
        
        String rest = title; // initialize rest
        
        Column col1 = allColumns.get(0);                    // the first column
        Column col2 = allColumns.get(allColumns.size() - 1);     // the last column
        if (col1.isSeparator) rowWidth -= col1.width;
        if (col2.isSeparator) rowWidth -= col2.width;
        
        // a temp column only for the title
        Column tempCol = new Column();
        // setup the temp column and print the title 
        tempCol.width(rowWidth);
        tempCol.horizontalCenter();
        
        // start printng
        while (!rest.isEmpty()){
            // if the leftmost column separator exists, then print it
            if (col1.isSeparator) textBuilder.append(col1.header);
            
            // print the title
            rest = printCell(tempCol, rest, true);
            
            // if the rightmost column separator exists, then print it
            if (col2.isSeparator) textBuilder.append(col2.header);
            
            // add new line
            textBuilder.append("\n");
        }
        
        // print a row separator below the title
        if (showTitleSeparator){
            for (int i = 0; i < allColumns.size(); i++){
                Column col = allColumns.get(i);

                if (col.isSeparator && col.header.equals(vertical) && i == 0)
                    textBuilder.append(horizontalStart);
                else if (col.isSeparator && col.header.equals(vertical) &&
                                                                    i == allColumns.size() - 1)
                    textBuilder.append(horizontalEnd);
                else if (col.isSeparator && col.header.equals(vertical))
                    textBuilder.append(horizontalDown);
                else
                    textBuilder.append(horizontal.repeat(col.width));
            }
            textBuilder.append("\n");
        }
    }
    
    
    private void printHeaders(){
        int arrayLength = 0;
        for (Column col : allColumns)
            if (!col.isSeparator)
                arrayLength++;
        
        // collect data columns into an array
        String[] forPrint = new String[arrayLength];
        int index = 0;
        for (Column col : allColumns){
            if (!col.isSeparator){
                forPrint[index] = col.header;
                index++;
            }
        }
        
        while (!isEmpty(forPrint)){
            int colIndex = 0;
            for (Column col : allColumns) {
                if (col.isSeparator)
                    printCell(col, col.header, true);
                else{
                    forPrint[colIndex] = printCell(col, forPrint[colIndex], true);
                    colIndex++;
                }
            }
            textBuilder.append("\n");
        }
        
        if (showHeadersSeparator) printRowSeparator();
    }
    
    
    private void printHeadersAndTitle(){
        if (showBorder && title != null){
            // find the row width
            int rowWidth = 0;
            for (Column col : allColumns)
                rowWidth += col.width;
            
            textBuilder.append(upLeftCorner);
            textBuilder.append(horizontal.repeat(rowWidth-2));
            textBuilder.append(upRightCorner);
            textBuilder.append("\n");   
        }
        else if (showBorder){ // regardless of showHeaders
            for (int i = 0; i < allColumns.size(); i++){
                Column col = allColumns.get(i);

                if (col.isSeparator && col.header.equals(vertical) && i == 0)
                    textBuilder.append(upLeftCorner);
                else if (col.isSeparator && col.header.equals(vertical) &&
                                                                    i == allColumns.size() - 1)
                    textBuilder.append(upRightCorner);
                else if (col.isSeparator && col.header.equals(vertical))
                    textBuilder.append(horizontalDown);
                else
                    textBuilder.append(horizontal.repeat(col.width));
            }
            textBuilder.append("\n");
        }

        if (title != null) printTitle();
        if (showHeaders) printHeaders();
    }
    
    
    private void addBorderSides(){
        Column c = new Column();
        c.width(vertical.length());
        c.header(vertical);
        c.horAlign = HORIZONTAL_CENTER;
        c.isSeparator = true;
        allColumns.add(0, c);
        allColumns.add(c);
    }
    
    
    private String printCell(Column col, String s, boolean write){
        String rest = "";
        String toPrint = s.substring(0, s.length());
        
        if (s.length() > col.width || s.contains("\n")){
            int split = -1;
            if (s.contains("\n"))
                split = s.indexOf('\n');
            
            int charsToConsume = 0;
            if (split < 0 || split > col.width)
                split = col.width;
            else if (split <= col.width)
                charsToConsume = 1;
            
            toPrint = s.substring(0, split);
            rest = s.substring(split + charsToConsume);
        }
        
        if (write){
            switch (col.horAlign) {
                case HORIZONTAL_RIGHT -> textBuilder.append(right(col.width, toPrint));
                case HORIZONTAL_LEFT -> textBuilder.append(left(col.width, toPrint));
                case HORIZONTAL_CENTER -> textBuilder.append(center(col.width, toPrint));
                // default is the same as HORIZONTAL_LEFT
                default -> textBuilder.append(left(col.width, toPrint));
            }
        }
        return rest;
    }

    
    private void printRowSeparator(){
        for (int i = 0; i < allColumns.size(); i++){
            Column col = allColumns.get(i);

            if (col.isSeparator && col.header.equals(vertical) && i == 0)
                textBuilder.append(horizontalStart);
            else if (col.isSeparator && col.header.equals(vertical) &&
                                                                i == allColumns.size() - 1)
                textBuilder.append(horizontalEnd);
            else if (col.isSeparator && col.header.equals(vertical))
                textBuilder.append(cross);
            else
                textBuilder.append(horizontal.repeat(col.width));
        }
        textBuilder.append("\n");
    }
    
    
    /* ***********************************
         P R I V A T E   U T I L I T I E S
       *********************************** */
    private String objectToString(Object obj, Column col){
        if (obj == null) return "";
        String result = obj.toString();
        if (obj instanceof Integer)
            result = Integer.toString((Integer) obj);

        else if (obj instanceof Long)
            result = Long.toString((Long) obj);

        else if (obj instanceof Float && col.decimals >= 0){
            float f = (float) obj;
            result = round((double) f, col.decimals);
        }

        else if (obj instanceof Float && col.decimals < 0)
            result = Float.toString((Float) obj);

        else if (obj instanceof Double && col.decimals >= 0)
            result = round((double) obj, col.decimals);

        else if (obj instanceof Double && col.decimals < 0)
            result = Double.toString((Double) obj);

        else if (obj instanceof String){
            String s = (String) obj;
            if (s.contains("\r") || s.contains("\n")){
                s = s.replace("\r\n", "\n");
                s = s.replace("\n\r", "\n");
                s = s.replace("\r", "\n");
            }
            result = s;
        }
//        else result = obj.toString();
        
        return result;
    }
    
    
    private boolean isEmpty(String[] arrayOfStrings){
        for (String s : arrayOfStrings)
            if (!s.isEmpty()) return false;
        return true;
    }
    
    
    private String right(int width, String s){
        int padd = width - s.length();
        return paddingChar.repeat(padd) + s;
    }
    
    private String left(int width, String s){
        int padd = width - s.length();
        return s + paddingChar.repeat(padd);
    }
    
    
    private String center(int width, String s){
        int padd = width - s.length();
        int leftPadd  = padd / 2;
        int rightPadd = padd / 2;
        if (padd % 2 == 1) leftPadd++;
        return paddingChar.repeat(leftPadd) + s + paddingChar.repeat(rightPadd);
    }
    
    
    private String round(double val, int numberOfDecimals){
        double shift = Math.pow(10.0, numberOfDecimals);
        double shiftedVal = Math.floor((val * shift) + 0.5);
        double rounded = shiftedVal / shift;
        return String.format("%."+ Integer.toString(numberOfDecimals) + "f", rounded);
    }
    
    
    /* ***************************
         C O L U M N   C L A S S
       *************************** */ 
    public class Column{
        protected int width = 12;
        protected String header = "";
        protected int horAlign = HORIZONTAL_LEFT;
        protected int verAlign = VERTICAL_TOP;
        protected int decimals = -1;
        protected boolean isSeparator = false;

        
        /**
         * Sets the width (in characters) for this column.
         * 
         * @param width the column width to set
         * @return the column object for chain invocation
         */
        public Column width(int width) {
            this.width = width;
            return this;
        }
        
        
        /**
         * Sets the header for this column, that is the column title.
         * 
         * {@code showHeaders()} must be set to {@code true} in order
         * to print the column headers.
         * 
         * @param header the header to set
         * @return the column object for chain invocation
         */
        public Column header(String header) {
            this.header = header;
            return this;
        }
        
        
        /**
         * Sets the horizontal alignment of this column to 'left'
         * 
         * @return the column object for chain invocation
         */
        public Column horizontalLeft(){
            this.horAlign = HORIZONTAL_LEFT;
            return this;
        }
        
        
        /**
         * Sets the horizontal alignment of this column to 'right'
         * 
         * @return the column object for chain invocation
         */
        public Column horizontalRight(){
            this.horAlign = HORIZONTAL_RIGHT;
            return this;
        }
        
        
        /**
         * Sets the horizontal alignment of this column to 'center'
         * 
         * @return the column object for chain invocation
         */
        public Column horizontalCenter(){
            this.horAlign = HORIZONTAL_CENTER;
            return this;
        }
        
        
        /**
         * Sets the vertical alignment of this column to 'top'
         * 
         * @return the column object for chain invocation
         */
        public Column verticalTop(){
            this.verAlign = VERTICAL_TOP;
            return this;
        }
        
        
        /**
         * Sets the vertical alignment of this column to 'bottom'
         * 
         * @return the column object for chain invocation
         */
        public Column verticalBottom(){
            this.verAlign = VERTICAL_BOTTOM;
            return this;
        }
        
        
        /**
         * Sets the vertical alignment of this column to 'middle'
         * 
         * @return the column object for chain invocation
         */
        public Column verticalMIddle(){
            this.verAlign = VERTICAL_MIDDLE;
            return this;
        }
        
        /**
         * Sets the number of decimals, in case of numerical data.
         * This is an optional parameter that controls the number of visible decimals
         * only if a cell contains a number. The number is rounded to the nearest double,
         * with the specified number of decimals. For example, if we set {@code decimals(2)}
         * then the number 130.0311 will be printed as 130.03 and 1 will be printed
         * as 1.00.
         * If a cell contains non-numerical data, then this parameter 
         * does not have any effect.
         * 
         * @param numberOfDecimals the number of visible decimals
         * @return the column object for chain invocation
         */
        public Column decimals(int numberOfDecimals){
            this.decimals = numberOfDecimals;
            return this;
        }
    }    
}
