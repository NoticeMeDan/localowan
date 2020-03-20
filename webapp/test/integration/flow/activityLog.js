import dayjs from 'dayjs'

describe('Test activity log', () => {
	beforeEach(() => {
		cy.server()

		cy.route('GET', '/api/log/1/*/*/0', {
			totalPages: 2,
			pageData: [
				{
					time: 1584375912,
					message: 'Heyo 1'
				},
				{
					time: 1584375913,
					message: 'Heyo 2'
				}
			]
		}).as('pageOne')

		cy.route('GET', '/api/log/1/*/*/1', {
			totalPages: 2,
			pageData: [
				{
					time: 1584375914,
					message: 'Heyo 3'
				},
				{
					time: 1584375915,
					message: 'Heyo 4'
				}
			]
		}).as('pageTwo')

		cy.visit('/flow/1')
	})

	it('Paginates correctly', () => {
		cy.contains(dayjs.unix(1584375912).format('DD/MM/YYYY H:mm:ss'))
		cy.contains('Heyo 1')
		cy.contains(dayjs.unix(1584375913).format('DD/MM/YYYY H:mm:ss'))
		cy.contains('Heyo 2')

		cy.get('button[title="Next page"]').click()

		cy.contains(dayjs.unix(1584375914).format('DD/MM/YYYY H:mm:ss'))
		cy.contains('Heyo 3')
		cy.contains(dayjs.unix(1584375915).format('DD/MM/YYYY H:mm:ss'))
		cy.contains('Heyo 4')
	})
})
