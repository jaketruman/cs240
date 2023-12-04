package chess;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Objects;

public class ChessPositionImplmentation implements ChessPosition {

    private int row;
    private int column;

    public ChessPositionImplmentation(int rowNum, int colNum){
        this.column=colNum;
        this.row = rowNum;
    }
    @Override
    public int getRow() {
        return row;
    }

    public void setRow(int i){
        row = i;
    }
    public void setColumn(int i ){
        column = i;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPositionImplmentation that = (ChessPositionImplmentation) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
    static class ListAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,ChessPositionImplmentation.class);
        }
    }
}