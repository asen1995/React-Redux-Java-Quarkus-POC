import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import type { TableEntry, CreateTableEntry } from '../types/TableEntry';
import { entriesApi } from '../api/entriesApi';

interface EntriesState {
  items: TableEntry[];
  loading: boolean;
  error: string | null;
}

const initialState: EntriesState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchEntries = createAsyncThunk('entries/fetchAll', async () => {
  return entriesApi.getAll();
});

export const addEntry = createAsyncThunk(
  'entries/add',
  async (entry: CreateTableEntry) => {
    return entriesApi.create(entry);
  }
);

export const deleteEntry = createAsyncThunk(
  'entries/delete',
  async (id: number) => {
    await entriesApi.delete(id);
    return id;
  }
);

const entriesSlice = createSlice({
  name: 'entries',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchEntries.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchEntries.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchEntries.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to fetch entries';
      })
      .addCase(addEntry.fulfilled, (state, action) => {
        state.items.push(action.payload);
      })
      .addCase(deleteEntry.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item.id !== action.payload);
      });
  },
});

export default entriesSlice.reducer;
