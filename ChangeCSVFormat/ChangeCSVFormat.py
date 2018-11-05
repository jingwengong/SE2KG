import csv
import sys


filename = 'inputsource/DelphiSequencesResult.csv'
#filename = sys.argv[1]
print(filename)
# ifile_dir = 'inputsource/' + filename
ifile_dir = filename
# ofile_dir = 'outputsource/' + filename + '_out'
ofile_dir = 'outputsource/headers'
first_row = []

with open(ifile_dir, mode = 'r') as csv_file:
    # print('Reading {ifile_dir}')
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    first_row = next(csv_reader)
# print (first_row)


with open(ofile_dir + '.csv', mode='w') as csv_file:
    print('Writing {ofile_dir}.txt')
    fieldnames = ['property']
    csv_writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
    csv_writer.writeheader()
    str = ""
    # csv_writer.writerows([first_row])
    for element in first_row:
        str += element
        csv_writer.writerow({'property' : element})
    print (str)
