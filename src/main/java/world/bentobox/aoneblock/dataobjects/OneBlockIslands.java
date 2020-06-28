package world.bentobox.aoneblock.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.EntityType;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.annotations.Expose;

import world.bentobox.aoneblock.oneblocks.OneBlockObject;
import world.bentobox.bentobox.database.objects.DataObject;

/**
 * @author tastybento
 *
 */
public class OneBlockIslands implements DataObject {

    @Expose
    private String uniqueId;
    @Expose
    private int blockNumber;
    @Expose
    private long lifetime;
    @Expose
    private String phaseName = "";

    private List<OneBlockObject> queue;

    /**
     * @return the phaseName
     */
    @NonNull
    public String getPhaseName() {
        return phaseName == null ? "" : phaseName;
    }

    /**
     * @param phaseName the phaseName to set
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public OneBlockIslands(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the blockNumber
     */
    public int getBlockNumber() {
        return blockNumber;
    }

    /**
     * @param blockNumber the blockNumber to set
     */
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    /**
     * Increments the block number
     */
    public void incrementBlockNumber() {
        this.blockNumber++;
    }

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.database.objects.DataObject#getUniqueId()
     */
    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.database.objects.DataObject#setUniqueId(java.lang.String)
     */
    @Override
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the queue
     */
    public List<OneBlockObject> getQueue() {
        if (queue == null) queue = new ArrayList<>();
        return queue;
    }

    /**
     * Get a list of nearby upcoming mobs
     * @param i - look ahead value
     * @return list of upcoming mobs
     */
    public List<EntityType> getNearestMob(int i) {
        if (queue == null) queue = new ArrayList<>();
        return queue.stream().limit(i).filter(OneBlockObject::isEntity).map(OneBlockObject::getEntityType).collect(Collectors.toList());
    }

    public void add(OneBlockObject nextBlock) {
        if (queue == null) queue = new ArrayList<>();
        queue.add(nextBlock);
    }

    public OneBlockObject pollAndAdd(OneBlockObject toAdd) {
        if (queue == null) queue = new ArrayList<>();
        OneBlockObject b = queue.get(0);
        queue.remove(0);
        queue.add(toAdd);
        return b;
    }

    /**
     * Clear the look ahead queue
     */
    public void clearQueue() {
        queue.clear();
    }

    /**
     * @return the loops
     */
    public long getLifetime() {
        return lifetime;
    }

    /**
     * @param loops the loops to set
     */
    public void setLifetime(long loops) {
        this.lifetime = loops;
    }


}
