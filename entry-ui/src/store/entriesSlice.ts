import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import type { TableEntry, CreateTableEntry } from '../types/TableEntry';
import { entriesApi } from '../api/entriesApi';

export type SortDirection = 'asc' | 'desc';
export type SortField = 'createdAt' | 'numberValue' | 'selectorValue' | 'freeText';

interface EntriesState {
  items: TableEntry[];
  loading: boolean;
  error: string | null;
  page: number;
  size: number;
  sortBy: SortField;
  sortDirection: SortDirection;
  totalPages: number;
  totalElements: number;
  hasNext: boolean;
  hasPrevious: boolean;
}

const initialState: EntriesState = {
  items: [],
  loading: false,
  error: null,
  page: 0,
  size: 10,
  sortBy: 'createdAt',
  sortDirection: 'desc',
  totalPages: 0,
  totalElements: 0,
  hasNext: false,
  hasPrevious: false,
};

interface FetchParams {
  page?: number;
  size?: number;
  sortBy?: SortField;
  sortDirection?: SortDirection;
}

export const fetchEntries = createAsyncThunk(
  'entries/fetchAll',
  async ({ page = 0, size = 10, sortBy = 'createdAt', sortDirection = 'desc' }: FetchParams) => {
    return entriesApi.getAll(page, size, sortBy, sortDirection);
  }
);

export const addEntry = createAsyncThunk(
  'entries/add',
  async (entry: CreateTableEntry, { dispatch, getState }) => {
    const result = await entriesApi.create(entry);
    const { size, sortBy, sortDirection } = (getState() as { entries: EntriesState }).entries;
    // Refresh the first page to show the new entry at the top
    dispatch(fetchEntries({ page: 0, size, sortBy, sortDirection }));
    return result;
  }
);

export const deleteEntry = createAsyncThunk(
  'entries/delete',
  async (id: number, { dispatch, getState }) => {
    await entriesApi.delete(id);
    const { page, size, sortBy, sortDirection } = (getState() as { entries: EntriesState }).entries;
    // Refresh current page after delete
    dispatch(fetchEntries({ page, size, sortBy, sortDirection }));
    return id;
  }
);

const entriesSlice = createSlice({
  name: 'entries',
  initialState,
  reducers: {
    setPageSize: (state, action) => {
      state.size = action.payload;
    },
    setSort: (state, action: { payload: { sortBy: SortField; sortDirection: SortDirection } }) => {
      state.sortBy = action.payload.sortBy;
      state.sortDirection = action.payload.sortDirection;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchEntries.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchEntries.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload.content;
        state.page = action.payload.page;
        state.totalPages = action.payload.totalPages;
        state.totalElements = action.payload.totalElements;
        state.hasNext = action.payload.hasNext;
        state.hasPrevious = action.payload.hasPrevious;
      })
      .addCase(fetchEntries.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to fetch entries';
      });
  },
});

export const { setPageSize, setSort } = entriesSlice.actions;
export default entriesSlice.reducer;
