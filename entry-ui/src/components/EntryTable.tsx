import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  TablePagination,
  TableSortLabel,
  Box,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { useAppSelector, useAppDispatch } from '../store/hooks';
import {
  deleteEntry,
  fetchEntries,
  setPageSize,
  setSort,
  type SortField,
} from '../store/entriesSlice';

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleString();
}

export function EntryTable() {
  const dispatch = useAppDispatch();
  const { items, loading, page, size, sortBy, sortDirection, totalElements } = useAppSelector(
    (state) => state.entries
  );

  const handlePageChange = (_: unknown, newPage: number) => {
    dispatch(fetchEntries({ page: newPage, size, sortBy, sortDirection }));
  };

  const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newSize = parseInt(event.target.value, 10);
    dispatch(setPageSize(newSize));
    dispatch(fetchEntries({ page: 0, size: newSize, sortBy, sortDirection }));
  };

  const handleSort = (field: SortField) => {
    const newDirection = sortBy === field && sortDirection === 'asc' ? 'desc' : 'asc';
    dispatch(setSort({ sortBy: field, sortDirection: newDirection }));
    dispatch(fetchEntries({ page: 0, size, sortBy: field, sortDirection: newDirection }));
  };

  if (loading) return <div>Loading...</div>;

  return (
    <Box>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell sortDirection={sortBy === 'createdAt' ? sortDirection : false}>
                <TableSortLabel
                  active={sortBy === 'createdAt'}
                  direction={sortBy === 'createdAt' ? sortDirection : 'asc'}
                  onClick={() => handleSort('createdAt')}
                >
                  Created At
                </TableSortLabel>
              </TableCell>
              <TableCell sortDirection={sortBy === 'numberValue' ? sortDirection : false}>
                <TableSortLabel
                  active={sortBy === 'numberValue'}
                  direction={sortBy === 'numberValue' ? sortDirection : 'asc'}
                  onClick={() => handleSort('numberValue')}
                >
                  Number
                </TableSortLabel>
              </TableCell>
              <TableCell sortDirection={sortBy === 'selectorValue' ? sortDirection : false}>
                <TableSortLabel
                  active={sortBy === 'selectorValue'}
                  direction={sortBy === 'selectorValue' ? sortDirection : 'asc'}
                  onClick={() => handleSort('selectorValue')}
                >
                  Selector
                </TableSortLabel>
              </TableCell>
              <TableCell sortDirection={sortBy === 'freeText' ? sortDirection : false}>
                <TableSortLabel
                  active={sortBy === 'freeText'}
                  direction={sortBy === 'freeText' ? sortDirection : 'asc'}
                  onClick={() => handleSort('freeText')}
                >
                  Free Text
                </TableSortLabel>
              </TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {items.map((entry) => (
              <TableRow key={entry.id}>
                <TableCell>{formatDate(entry.createdAt)}</TableCell>
                <TableCell>{entry.numberValue}</TableCell>
                <TableCell>{entry.selectorValue}</TableCell>
                <TableCell>{entry.freeText}</TableCell>
                <TableCell>
                  <IconButton
                    size="small"
                    onClick={() => dispatch(deleteEntry(entry.id))}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        component="div"
        count={totalElements}
        page={page}
        onPageChange={handlePageChange}
        rowsPerPage={size}
        onRowsPerPageChange={handleRowsPerPageChange}
        rowsPerPageOptions={[5, 10, 20, 50]}
      />
    </Box>
  );
}
