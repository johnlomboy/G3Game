package com.example.john.samplegame;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Philip on 6/4/2015.
 */
public class GameActivity extends Activity {

    private static final String TAG = "MainActivity";
    public static final int gridSize = 6;

    //    @InjectView(R.id.glGameboard)
    private GridLayout mGameboard;
    private RelativeLayout mParent;
    int[][] mBoardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameboard = (GridLayout) findViewById(R.id.glGameboard);
        mParent = (RelativeLayout) findViewById(R.id.mParent);
        initializeGameboard();
    }

    private void initializeGameboard() {
        int gridPadding = 18;
        mBoardLayout = new int[gridSize][gridSize];

//        gridPadding *= 2;

        mGameboard.setRowCount(gridSize);
        mGameboard.setColumnCount(gridSize);
        mGameboard.setPadding(dpToPixels(gridPadding), 0, dpToPixels(gridPadding), 0);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                final int ans = new Random().nextInt(2);

                ImageView imageView = new ImageView(this);
                if (ans == 0) {
                    imageView.setBackgroundResource(R.mipmap.ic_launcher);
                } else {
                    imageView.setBackgroundResource(R.mipmap.ic_launcher_black);
                }

                final int finalRow = row;
                final int finalCol = col;

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkSimilarCell(finalRow, finalCol);
                    }
                });
                mGameboard.addView(imageView);
                mBoardLayout[row][col] = ans;
            }
        }
        displayGrid();
    }

    private void checkSimilarCell(int row, int col) {
        ArrayList<Integer> cellIndexList = new ArrayList<>();
        ArrayList<Integer> rowList = new ArrayList<>();
        ArrayList<Integer> columnList = new ArrayList<>();

        final int cellType = mBoardLayout[row][col];

        rowList.add(row);
        columnList.add(col);
        while (rowList.size() != 0 && columnList.size() != 0) {
//            Log.e(TAG, "CellIndexList " + cellIndexList.size());

            final int rowIndex = rowList.get(0);
            final int colIndex = columnList.get(0);

//            Log.e(TAG, "Row Index " + rowIndex);
//            Log.e(TAG, "Column Index " + colIndex);

            if (!isCellIndexAvailable(cellIndexList, rowIndex, colIndex)) {
                cellIndexList.add(getCellIndex(rowList.get(0), columnList.get(0)));
                // up
                if (rowIndex - 1 >= 0) {
                    if (cellType == mBoardLayout[rowIndex - 1][colIndex]) {
//                        Log.e(TAG, "UP : Adding cell at Row : " + rowIndex + ", Column : " + colIndex);
                        rowList.add(rowIndex - 1);
                        columnList.add(colIndex);
                    }
                }

                // right
                if (colIndex + 1 <= gridSize - 1) {
                    if (cellType == mBoardLayout[rowIndex][colIndex + 1]) {
//                        Log.e(TAG, "RIGHT : Adding cell at Row : " + rowIndex + ", Column : " + colIndex);
                        rowList.add(rowIndex);
                        columnList.add(colIndex + 1);
                    }
                }

                //down
                if (rowIndex + 1 <= gridSize - 1) {
                    if (cellType == mBoardLayout[rowIndex + 1][colIndex]) {
//                        Log.e(TAG, "DOWN : Adding cell at Row : " + rowIndex + ", Column : " + colIndex);
                        rowList.add(rowIndex + 1);
                        columnList.add(colIndex);
                    }
                }


                // left
                if (colIndex - 1 >= 0) {
                    if (cellType == mBoardLayout[rowIndex][colIndex - 1]) {
//                        Log.e(TAG, "LEFT : Adding cell at Row : " + rowIndex + ", Column : " + colIndex);
                        rowList.add(rowIndex);
                        columnList.add(colIndex - 1);
                    }
                }
            }

            rowList.remove(0);
            columnList.remove(0);
        }

        for (int i = 0; i < cellIndexList.size(); i++) {
            Log.e(TAG, "Remove cells at index " + cellIndexList.get(i));
        }
        removeCells(cellIndexList);
    }

    private boolean isCellIndexAvailable(ArrayList<Integer> cellIndexList, int rowIndex, int colIndex) {
        boolean isAvailable = false;
        for (Integer cellIndex : cellIndexList) {
            if (cellIndex == getCellIndex(rowIndex, colIndex)) {
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    private int getCellIndex(int row, int col) {
        int cellIndex = 0;
        if (row == 0) {
            cellIndex += col;
        } else {
            cellIndex += row * gridSize + col;
        }
//        Log.e(TAG, "Returned cell index " + cellIndex);
        return cellIndex;
    }

    private void removeCells(ArrayList<Integer> cellIndex) {
        if (cellIndex.size() >= 3) {
            for (Integer index : cellIndex) {
                mGameboard.getChildAt(index).setBackgroundResource(generateCell(index));
            }
        } else {
            Snackbar.make(mParent, "Must have at least 3 cells connected", Snackbar.LENGTH_LONG)
                    .setAction("Toast", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getBaseContext(), "Must have at least 3 cells connected", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        }
        displayGrid();
    }

    private int generateCell(int cellIndex) {
        /**
         * re-store mBoardLayout with the generated cells
         */
//        int randCellType = new Random().nextInt(2);
//        int resId = 0;
        int randCellType = 3;
        int resId = R.mipmap.ic_launcher_blue;

        modifyCellType(randCellType, cellIndex);

//        if (randCellType == 0) {
//            resId = R.mipmap.ic_launcher;
//        } else {
//            resId = R.mipmap.ic_launcher_black;
//        }
        return resId;
    }

    private void modifyCellType(int cellType, int cellIndex) {
        // TODO : BUGGGG!
        /**
         * Note : To get row and column use this formula.
         * row = cellIndex / gridSize;
         * column = cellindex - (gridSize * row);
         */

        final int row = cellIndex / gridSize;
        final int col = cellIndex - (gridSize * row);

//        Log.e(TAG, "Row " + row + " Column " + col);

        mBoardLayout[row][col] = cellType;
    }

    private int dpToPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Used to log grid for reference.
     */
    private void displayGrid() {
        String gridLog = "1\n";
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridLog += ("[" + mBoardLayout[row][col] + "]");
            }
            gridLog += "\n";
        }
        Log.e(TAG, gridLog);
    }
}
