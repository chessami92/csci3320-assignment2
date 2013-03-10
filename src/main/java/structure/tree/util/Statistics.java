package structure.tree.util;

/**
 * Created by: Josh
 * On: 2/17/13 5:36 PM
 */
public class Statistics {
    //Used for keeping track of tree performance.
    private int maxSearch = -1, minSearch = -1;
    private int maxRotations = -1, minRotations = -1;
    private int totalSearch = 0, searchSamples = 0;
    private int totalRotations = 0, rotationSamples = 0;
    private int totalHeight = 0, heightSamples = 0;

    public void updateSearch(int searchCount) {
        //Add the search count into the running total.
        totalSearch += searchCount;
        ++searchSamples;

        //Set the min and max search counts as necessary. Starts out at -1, so always replace at beginning.
        if (maxSearch == -1) {
            maxSearch = searchCount;
            minSearch = searchCount;
        } else if (searchCount > maxSearch)
            maxSearch = searchCount;
        else if (searchCount < minSearch)
            minSearch = searchCount;
    }

    public int getMaxSearch() {
        return maxSearch;
    }

    public int getMinSearch() {
        return minSearch;
    }

    public int getAverageSearch(){
        //Check to avoid divide by 0 error.
        if(searchSamples == 0)
            throw new IllegalStateException("Must update search before average search available.");

        return totalSearch / searchSamples;
    }

    public void updateRotations(int rotationCount) {
        //Add the rotation count into the running total.
        totalRotations += rotationCount;
        ++rotationSamples;

        //Set the min and max rotation counts as necessary. Starts out at -1, so always replace at beginning.
        if (maxRotations == -1) {
            maxRotations = rotationCount;
            minRotations = rotationCount;
        } else if (rotationCount > maxRotations)
            maxRotations = rotationCount;
        else if (rotationCount < minRotations)
            minRotations = rotationCount;
    }

    public int getMaxRotations() {
        return maxRotations;
    }

    public int getMinRotations() {
        return minRotations;
    }

    public int getAverageRotations() {
        //Check to avoid divide by 0 error.
        if(rotationSamples == 0)
            throw new IllegalStateException("Must update rotations before average rotation available.");

        return totalRotations / rotationSamples;
    }

    public void updateHeight (int height){
        //Add the height into the running total.
        totalHeight += height;
        ++heightSamples;
    }

    public int getAverageHeight(){
        //Check to avoid divide by 0 error.
        if(heightSamples == 0)
            throw new IllegalStateException("Must update rotations before average rotation available.");

        return totalHeight / heightSamples;
    }
}
