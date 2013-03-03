package structure.tree.util;

/**
 * Created by: Josh
 * On: 2/17/13 5:36 PM
 */
public class Statistics {
    private int maxSearch = -1, minSearch = -1;
    private int maxRotations = -1, minRotations = -1;
    private int totalSearch = 0, searchSamples = 0;
    private int totalRotations = 0, rotationSamples = 0;
    private int totalHeight = 0, heightSamples = 0;

    public void updateSearch(int searchCount) {
        totalSearch += searchCount;
        ++searchSamples;

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
        return totalSearch / searchSamples;
    }

    public void updateRotations(int rotationCount) {
        if (maxRotations == -1) {
            maxRotations = rotationCount;
            minRotations = rotationCount;
        } else if (rotationCount > maxRotations)
            maxRotations = rotationCount;
        else if (rotationCount < minRotations)
            minRotations = rotationCount;

        totalRotations += rotationCount;
        ++rotationSamples;
    }

    public int getMaxRotations() {
        return maxRotations;
    }

    public int getMinRotations() {
        return minRotations;
    }

    public int getAverageRotations() {
        if(rotationSamples == 0)
            throw new IllegalStateException("Must update rotations before average rotation available.");

        return totalRotations / rotationSamples;
    }

    public void updateHeight (int height){
        totalHeight += height;
        ++heightSamples;
    }

    public int getAverageHeight(){
        if(heightSamples == 0)
            throw new IllegalStateException("Must update rotations before average rotation available.");

        return totalHeight / heightSamples;
    }
}
