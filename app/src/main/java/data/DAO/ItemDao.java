package data.DAO;

/**
 * Created by mxion on 10/6/2017.
 */

public class ItemDao {

    private int Id ;
    private int ListId;
    private String Name;
    private String StoreName;
    private boolean CrossOff;
    private String NoteText;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getListId() {
        return ListId;
    }

    public void setListId(int listId) {
        ListId = listId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public boolean isCrossOff() {
        return CrossOff;
    }

    public void setCrossOff(boolean crossOff) {
        CrossOff = crossOff;
    }

    public String getNoteText() {
        return NoteText;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }
}
